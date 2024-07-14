//package vn.dattb.auth.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
////@Component
//public class RedirectRequestHeaderFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
//        String uri = servletRequest.getRequestURI();
//
//        URL url = new URL("http://localhost:8080" + uri);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        logger.info("Redirect request header filter");
//        try {
//            // Set request method, headers, etc.
//            connection.setRequestMethod("GET"); // Or "POST", etc.
//            // Forward headers from the incoming request
//            String forwardedFor = servletRequest.getHeader("X-Forwarded-For");
//            if (forwardedFor != null) {
//                connection.setRequestProperty("X-Forwarded-For", forwardedFor);
//            }
//            String forwardedHost = servletRequest.getHeader("X-Forwarded-Host");
//            if (forwardedHost != null) {
//                connection.setRequestProperty("X-Forwarded-Host", forwardedHost);
//            }
//            String forwardedProto = servletRequest.getHeader("X-Forwarded-Proto");
//            if (forwardedProto != null) {
//                connection.setRequestProperty("X-Forwarded-Proto", forwardedProto);
//            }
//
//            // Connect and read the response
//            InputStream inputStream = connection.getInputStream();
//            OutputStream outputStream = servletResponse.getOutputStream();
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//            logger.info("Redirect request header filter");
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//
//
//    }
//}
