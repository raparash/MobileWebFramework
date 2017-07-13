/**
 * 
 */
package com.oracle.web.fmwk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.web.servlet.WebController;
import com.oracle.web.util.StringUtil;

/**
 * @author raparash
 *
 */
public class AnnotationHandler {

	private static volatile boolean isInitialized;
	private static Map<String,HandlerMap> mapOfhandlers=new HashMap<String,HandlerMap>();
	private static final Logger LOGGER=Logger.getLogger(AnnotationHandler.class.getName());
	
	/**
	 * Initializes all the controllers
	 * 
	 * @param configName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean init(String configName) {
		BufferedReader bufferedReader = null;
		String className = null;
		try {
			if (!isInitialized) {
				bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(configName)));
					while ((className = bufferedReader.readLine()) != null) {
						className = StringUtil.safeTrim(className);
						Class<?> cls = Class.forName(className);
						Object object = cls.newInstance();
						if (!(object instanceof WebController))
							throw new ClassCastException(className
									+ " is not the type of WebController. Please implement WebController interface ");
						List<Method> methodList=getMethods(cls, RequestMapping.class);
						mapOfhandlers.putAll(getRequestMappedMethodMap(methodList,(Class<? extends WebController>) cls));
					}
					isInitialized = true;
			}
		} catch (Throwable t) {
			LOGGER.log(Level.WARNING,"Exception|AnnotationHandler|init() ", t);
		}
		return isInitialized;
	}

	/**
	 * Comments to be written......
	 * @param urlPattern
	 * @return
	 * @throws InterruptedException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Class<? extends WebController> getController(String configName,String urlPattern) throws InterruptedException, InstantiationException, IllegalAccessException {
		Class<? extends WebController> webControllerClass=null;
		HandlerMap handlerMap=null;
			if(mapOfhandlers.isEmpty()){
				init(configName);
			}
			handlerMap=mapOfhandlers.get(urlPattern);
			webControllerClass=handlerMap.getWebControllerClass();
		
		return webControllerClass;

	}
	
	/**
	 * Invokes the controller method
	 * 
	 * @param urlPattern
	 * @param args
	 * @return
	 */
	public static Object invokeControllerMethod(String urlPattern, Object...args){
		HandlerMap handlerMap=null;
		Object result=null;
		try{
			handlerMap=mapOfhandlers.get(urlPattern);
			LOGGER.log(Level.INFO,"Inside|AnnotationHandler|invokeControllerMethod()| handlerMap= "+handlerMap);
			Class<? extends WebController> webControllerClass=handlerMap.getWebControllerClass();
			WebController webController=webControllerClass.newInstance();
			String methodToInvoke=handlerMap.getMethodName();
			Class<?>[] params=handlerMap.getMethodParams();
			Method method=webControllerClass.getMethod(methodToInvoke, params);
			result=method.invoke(webController, args);
		}catch(Throwable t){
			LOGGER.log(Level.WARNING,"Exception|AnnotationHandler|invokeControllerMethod() ", t);
		}
		return result;
	}

	/**
	 * returns a longest matching url pattern
	 * @param urlPattern
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String longestMatchingUrlpattern(String urlPattern){
		int minIndexOfpattern=Integer.MAX_VALUE;
		String longestMatchedUrlPattern=null;
		for(Entry<String,HandlerMap> entry:mapOfhandlers.entrySet()){
			String key=entry.getKey();
			int matchingPosition=urlPattern.lastIndexOf(key);
			if(matchingPosition!=-1){
				if(matchingPosition<minIndexOfpattern && urlPattern.endsWith(key)){
					longestMatchedUrlPattern=key;
				}
			}
		}
		return longestMatchedUrlPattern;
	}
	
	/**
	 * returns a list of methods having a given annotation
	 * @param cls
	 * @param classOfAnnotation
	 * @return
	 */
	private static List<Method> getMethods(Class<?> cls, Class<? extends Annotation> classOfAnnotation){
		Method[] declaredMethods=cls.getDeclaredMethods();
		List<Method> annotatedMethods=new ArrayList<Method>();
		for(Method method:declaredMethods){
			Annotation annotation=method.getAnnotation(classOfAnnotation);
			if(annotation!=null){
				annotatedMethods.add(method);
			}
		}
		return annotatedMethods;
	}

	/**
	 *  Creates a map of Request Handlers
	 * @param listOfAnnotatedMethods
	 * @param cls
	 * @return
	 */
	private static Map<String,HandlerMap> getRequestMappedMethodMap(List<Method> listOfAnnotatedMethods,Class<? extends WebController> cls){
		Map<String,HandlerMap> mapOfAnnotations=new HashMap<String,HandlerMap>();
		for(Method method:listOfAnnotatedMethods){
			RequestMapping requestMapAnnotation=method.getAnnotation(RequestMapping.class);
			HandlerMap handlerMap=new HandlerMap();
			handlerMap.setWebControllerClass(cls);
			handlerMap.setMethodName(method.getName());
			handlerMap.setMethodParams(method.getParameterTypes());
			mapOfAnnotations.put(requestMapAnnotation.value(), handlerMap);
		}
		return mapOfAnnotations;
	}
}
