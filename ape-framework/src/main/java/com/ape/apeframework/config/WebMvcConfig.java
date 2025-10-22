package com.ape.apeframework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private Optional<Path> findTarget(String start, String targetDirName) {
        Path startPath = Paths.get(start);
        try {
            return Files.walk(startPath, 4) // 搜索深度 4（根据需要调整）
                    .filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().equals(targetDirName))
                    .findFirst();
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String cwd = System.getProperty("user.dir"); // E:/A_JAVA/learn（你的工作目录）
        Optional<Path> opt = findTarget(cwd, "learn_server-master");
        Path base = opt.orElse(Paths.get(cwd)); // 未找到则回退到 cwd

        String imgLocation = base.resolve("img").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/img/**").addResourceLocations(imgLocation);

        System.out.println("Auto-detected img location: " + imgLocation);
    }
}
