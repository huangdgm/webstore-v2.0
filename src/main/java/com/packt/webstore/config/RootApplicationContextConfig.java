package com.packt.webstore.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("com.packt.webstore")
public class RootApplicationContextConfig {
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();

		/**
		 * I replaced 5 addScript method with 2 addScript method, then the error
		 * message has gone. Why?
		 * 
		 * 5 addScript method: 
		 * .addScript("db/sql/create-customers-table.sql")
		 * .addScript("db/sql/create-cart-table.sql")
		 * .addScript("db/sql/create-products-table.sql")
		 * .addScript("db/sql/insert-products-data.sql")
		 * .addScript("db/sql/insert-customers-data.sql")
		 */
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL).addScript("db/sql/create-table.sql")
				.addScript("db/sql/insert-data.sql").build();

		return db;
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
}
