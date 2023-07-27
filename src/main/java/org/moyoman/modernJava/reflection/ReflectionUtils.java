package org.moyoman.modernJava.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
/** Methods for using reflection to read annotations on methods.
 *  This is not currently being used, but if more fine grained
 *  authorization at the method level is implemented, this code
 *  can be modified to support that.
 *
 */
public class ReflectionUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);
	
	public String getApiPathFromMethod(String className, String apiMethod) throws Exception {
		String classApi = "Unknown";
		
		Class clazz = ClassLoader.getSystemClassLoader().loadClass(className);
		LOGGER.debug("class is {}", clazz.getCanonicalName());
		
		Annotation[] classAnnotations = clazz.getAnnotations();
		for (Annotation annotation : classAnnotations) {
			LOGGER.debug("Class annotation is {}", annotation.annotationType().getCanonicalName());
			if (annotation.annotationType().getCanonicalName().
					equals("org.springframework.web.bind.annotation.RequestMapping")) {
				RequestMapping requestMapping = (RequestMapping) annotation;
				classApi = requestMapping.value()[0];
				LOGGER.debug("Class path is {}", classApi);
			}
		}
		
		Method method = getMethod(clazz, apiMethod);
		LOGGER.debug("Found method {}", method.getName());
		String methodApi = getAnnotations(method);
		String fullPath = classApi + methodApi;
		LOGGER.info("Found url call to {}", fullPath);
		
		return fullPath;
	}
	
	public Method getMethod(Class clazz, String name) {
		Method[] methods = clazz.getDeclaredMethods();
		Method matchedMethod = null;
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				matchedMethod = method;
			}
		}
		
		return matchedMethod;
		
	}

	@SuppressWarnings("unchecked")
	public String getAnnotations(Method method) {
		Annotation[] annotations = method.getAnnotations();
		String path = "Unknown";
		
		for (Annotation annotation : annotations) {
			String canonicalName = annotation.annotationType().getCanonicalName();
			
			// API documentation decorator, not security related, so ignore.
			if (canonicalName.startsWith("io.swagger")) {
				continue;
			}
			
			switch(canonicalName) {
			case "org.springframework.web.bind.annotation.GetMapping" :
				GetMapping getAnnotation = (GetMapping) annotation;
				path = getAnnotation.value()[0];
				LOGGER.debug("Value for GET is {}", path);
				break;
			case "org.springframework.web.bind.annotation.PostMapping" :
				PostMapping postAnnotation = (PostMapping) annotation;
				path = postAnnotation.value()[0];
				LOGGER.debug("Value for POST is {}", path);
				break;
			case "org.springframework.web.bind.annotation.PutMapping" :
				PutMapping putAnnotation = (PutMapping) annotation;
				path = putAnnotation.value()[0];
				LOGGER.debug("Value for PUT is {}", path);
				break;
			case "org.springframework.web.bind.annotation.DeleteMapping" :
				DeleteMapping deleteAnnotation = (DeleteMapping) annotation;
				path = deleteAnnotation.value()[0];
				LOGGER.debug("Value for DELETE is {}", path);
			default:
				LOGGER.warn("Unsupported annotation type {} for {}", annotation.annotationType().getCanonicalName());
				
			}
			LOGGER.debug("Annotation is {}", annotation.annotationType().getCanonicalName());
		}
		
		return path;
	}
}
