package com.group5.firstpassport.util;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OracleContextFilter extends OncePerRequestFilter {
  JdbcTemplate jdbcTemplate;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.isAuthenticated()) {
      String username = auth.getName();
      String role = auth.getAuthorities().iterator().next().getAuthority();

      // Xóa prefix ROLE_ nếu có
      if (role.startsWith("ROLE_")) {
        role = role.substring(5);
      }

      final String finalUsername = username;
      final String finalRole = role;

      jdbcTemplate.execute((Connection conn) -> {
        try (CallableStatement cs = conn.prepareCall("{call set_passport_ctx_pkg.set_user_info(?, ?)}")) {
          cs.setString(1, finalUsername);
          cs.setString(2, finalRole);
          cs.execute();
        }
        return null;
      });
    }

    try {
      filterChain.doFilter(request, response);
    } finally {
      jdbcTemplate.execute((Connection conn) -> {
        try (CallableStatement cs = conn.prepareCall("{call set_passport_ctx_pkg.clear_user_info()}")) {
          cs.execute();
        }
        return null;
      });
    }
  }
}
