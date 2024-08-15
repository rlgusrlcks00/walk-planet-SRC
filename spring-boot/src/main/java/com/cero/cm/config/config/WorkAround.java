package com.cero.cm.config.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Component
public class WorkAround implements WebMvcOpenApiTransformationFilter {

	@Autowired
	private Environment environment;

	@Override
	public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
		OpenAPI openApi = context.getSpecification();
		List<Server> serverList = new LinkedList<>();
		if (environment.getActiveProfiles()[0].equals("dev")) {
			Server devServer = new Server();
			devServer.setDescription("dev");
			devServer.setUrl("https://app-dev.cb-zero.com/");
			serverList.add(devServer);
		} else if (environment.getActiveProfiles()[0].equals("prod")) {
			Server prodServer = new Server();
			prodServer.setDescription("prod");
			prodServer.setUrl("https://app.cb-zero.com/");
			serverList.add(prodServer);
		} else {
			Server localServer = new Server();
			localServer.setDescription("local");
			localServer.setUrl("http://localhost:8030");
			serverList.add(localServer);
		}
		openApi.setServers(serverList);

		return openApi;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return delimiter.equals(DocumentationType.OAS_30);
	}
}
