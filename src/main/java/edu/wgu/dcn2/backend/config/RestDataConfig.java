package edu.wgu.dcn2.backend.config;

import edu.wgu.dcn2.backend.entities.Country;
import edu.wgu.dcn2.backend.entities.Customer;
import edu.wgu.dcn2.backend.entities.Division;
import edu.wgu.dcn2.backend.entities.Excursion;
import edu.wgu.dcn2.backend.entities.Vacation;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * <h1>RestDataConfig</h1>
 * Per course instructions, include this code to configure
 * the REST API endpoints exposed for the project.
 *
 * @author WGU Course Materials
 * @version 0.1
 * @since 2023-02-27
 */
@Configuration
public class RestDataConfig implements RepositoryRestConfigurer {

    /**
     * Exposes standard REST API endpoints for the following classes:
     * Country
     * Customer
     * Division
     * Excursion
     * Vacation
     */
    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        config.exposeIdsFor(Country.class);
        config.exposeIdsFor(Customer.class);
        config.exposeIdsFor(Division.class);
        config.exposeIdsFor(Excursion.class);
        config.exposeIdsFor(Vacation.class);

        config.setDefaultPageSize(Integer.MAX_VALUE);
        config.setMaxPageSize(Integer.MAX_VALUE);
    }
}
