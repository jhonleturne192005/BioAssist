package com.api.asistencia;

import com.api.asistencia.models.ModelCurso;
import com.api.asistencia.models.ModelDiasSemana;
import com.api.asistencia.models.ModelGenero;
import com.api.asistencia.models.ModelHorario;
import com.api.asistencia.models.ModelMaterias;
import com.api.asistencia.models.ModelMateriasPorPersona;
import com.api.asistencia.models.ModelMatriculacion;
import com.api.asistencia.models.ModelPersona;
import com.api.asistencia.models.ModelTipoPersona;
import com.api.asistencia.service.SAsistencia;
import com.api.asistencia.service.SCurso;
import com.api.asistencia.service.SDiaSemana;
import com.api.asistencia.service.SGenero;
import com.api.asistencia.service.SHorario;
import com.api.asistencia.service.SMaterias;
import com.api.asistencia.service.SMateriasPorPersona;
import com.api.asistencia.service.SMatriculacion;
import com.api.asistencia.service.SPersona;
import com.api.asistencia.service.STipoPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AsistenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsistenciaApplication.class, args);
	}
        
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                        registry.addMapping("/api/**")
                                .allowedOrigins("http://localhost:5173")
                                .allowedMethods("GET", "POST", "PUT", "DELETE")
                                .allowedHeaders("*")
                                .allowCredentials(true);

                }
            };
        }
}
