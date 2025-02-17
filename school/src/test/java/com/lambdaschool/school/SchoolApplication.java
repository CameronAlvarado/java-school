package com.lambdaschool.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
//@EnableJpaAuditing  //  <-- needed for custom exception handlers | comment out for testing
@SpringBootApplication
public class SchoolApplication
{

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(SchoolApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet"); // rest exceptions need proper spelling
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }


}
