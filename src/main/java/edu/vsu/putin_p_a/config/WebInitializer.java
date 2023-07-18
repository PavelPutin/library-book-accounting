package edu.vsu.putin_p_a.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        addHiddenMethodFilter(servletContext);
    }

    private void addHiddenMethodFilter(ServletContext servletContext) {
        servletContext
                .addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null, true, "/*");
    }
}
