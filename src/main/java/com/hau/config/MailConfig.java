package com.hau.config;

import com.hau.dto.BillDTO;
import com.hau.dto.CartItemDTO;
import com.hau.service.CurrencyFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("thanhcao9323@gmail.com");
        mailSender.setPassword("fohw hack cygj ftyw");

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        mailSender.setJavaMailProperties(props);

        return mailSender;

    }

    public static void sendEmail(String email , List<CartItemDTO> cartItemDTOS, BillDTO billDTO , JavaMailSender mailSender, CurrencyFormat currencyFormat) throws MessagingException, UnsupportedEncodingException {
        // Tạo đối tượng MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

// Thiết lập thông tin người gửi và người nhận
        helper.setFrom("thanhcao9323@gmail.com", "ChronoLux Support");
        helper.setTo(email);
        helper.setSubject("Chúc mừng bạn đã đặt hàng thành công!");


// Nội dung email với CSS để thiết kế các ô sản phẩm và tổng cộng
        StringBuilder content = new StringBuilder("<p style='font-family: Arial, sans-serif; color: #333; margin: 20px; font-size: 28px;'><strong>Thông tin đơn hàng:</strong></p>");

// Sử dụng vòng lặp để thêm từng sản phẩm vào nội dung email
        String productIndex1 = "1";
        for (CartItemDTO product : cartItemDTOS) {
            String productName = product.getProductName();
            String productDescription = product.getProductType();
            double productPrice =  Double.parseDouble(product.getProductPrice()) ;
            int productQuantity = Integer.parseInt(product.getQuantity()) ;


            content.append("<div style='border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 10px 0; display: flex; align-items: flex-start;'>")
                    .append("<img src='cid:productImage").append(productIndex1).append("' alt='Product Image' style='width: 80px; height: 100px; border: 1px solid #ccc; border-radius: 5px; margin-right: 10px;'/>")
                    .append("<div style='flex-grow: 1;'>")
                    .append("<h1 style='color: #007BFF; font-size: 18px; margin: 0;'>").append(productName).append("</h1>")
                    .append("<p style='margin: 5px 0;'><strong>Loại máy:</strong> ").append(productDescription).append("</p>")
                    .append("<p style='margin: 5px 0;'><strong>Giá:</strong> ").append(currencyFormat.formatCurrency(productPrice) ).append("</p>")
                    .append("<p style='margin: 5px 0;'><strong>Số lượng:</strong> ").append(productQuantity).append("</p>")
                    .append("</div></div>");
            productIndex1 = String.valueOf (Integer.parseInt(productIndex1) +1);
        }

// Phần tổng cộng
        // Thông tin khách hàng
        content.append("<div style='text-align: right; margin-top: 20px;'>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Tổng giá trị sản phẩm: ").append(currencyFormat.formatCurrency(billDTO.getSubtotal()) ).append("</p>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Khuyến mãi: ").append(currencyFormat.formatCurrency(billDTO.getDiscount()) ).append("</p>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Tổng cộng: ").append(currencyFormat.formatCurrency(billDTO.getTotal()) ).append("</p>")
                .append("</div>")

// Thông tin khách hàng
                .append("<div style='border-top: 1px solid #ccc; padding-top: 10px; margin-top: 20px;'>")
                .append("<h2 style='font-family: Arial, sans-serif; color: #333; font-size: 16px;'>Thông tin khách hàng</h2>")
                .append("<p><strong>Họ và tên:</strong> ").append(billDTO.getDisplayName()).append("</p>")
                .append("<p><strong>Email:</strong> ").append(billDTO.getEmail()).append("</p>")
                .append("<p><strong>Số điện thoại:</strong> ").append(billDTO.getPhone()).append("</p>")
                .append("<p><strong>Địa chỉ:</strong> ").append(billDTO.getStreet()).append("-").append(billDTO.getWard()).append("-").append(billDTO.getDistrict()).append("-").append(billDTO.getCity()).append("</p>")
                .append("</div>");


// Thiết lập nội dung email
        helper.setText(content.toString(), true);
        String productIndex2 = "1";
        for(CartItemDTO product : cartItemDTOS){
            FileSystemResource image = new FileSystemResource(new File("E:/apache-tomcat-8.5.92/apache-tomcat-8.5.92/webapps/ChronoLuxWeb/template/web/img/products/"+product.getProductImgUrl() ));
            helper.addInline("productImage" + productIndex2, image, "image/webp");
            productIndex2 = String.valueOf (Integer.parseInt(productIndex2) +1);
        }

// Gửi email
        mailSender.send(message);
    }

    public static void sendEmailToAdmin(String email , List<CartItemDTO> cartItemDTOS, BillDTO billDTO , JavaMailSender mailSender, CurrencyFormat currencyFormat) throws MessagingException, UnsupportedEncodingException {
        // Tạo đối tượng MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

// Thiết lập thông tin người gửi và người nhận
        helper.setFrom("contact@chronolux.com", "ChronoLux Support");
        helper.setTo(email);
        helper.setSubject("Thông báo: Đơn hàng mới vừa được đặt!");


        StringBuilder content = new StringBuilder("<p style='font-family: Arial, sans-serif; color: #333; margin: 20px; font-size: 28px;'><strong>Thông báo đơn hàng mới:</strong></p>");

// Hiển thị mã đơn hàng (nếu có)
        content.append("<p style='font-size: 16px;'><strong>Mã đơn hàng:</strong> ").append(billDTO.getId()).append("</p>");

// Hiển thị thông tin khách hàng
        content.append("<p style='font-size: 16px;'><strong>Khách hàng:</strong> ").append(billDTO.getDisplayName()).append("</p>")
                .append("<p style='font-size: 16px;'><strong>Số điện thoại:</strong> ").append(billDTO.getPhone()).append("</p>")
                .append("<p style='font-size: 16px;'><strong>Địa chỉ:</strong> ").append(billDTO.getStreet()).append("-").append(billDTO.getWard()).append("-").append(billDTO.getDistrict()).append("-").append(billDTO.getCity()).append("</p>");

// Sử dụng vòng lặp để thêm từng sản phẩm vào nội dung email
        String productIndex1 = "1";
        for (CartItemDTO product : cartItemDTOS) {
            String productName = product.getProductName();
            String productDescription = product.getProductType();
            double productPrice = Double.parseDouble(product.getProductPrice());
            int productQuantity = Integer.parseInt(product.getQuantity());

            content.append("<div style='border: 1px solid #ccc; border-radius: 5px; padding: 10px; margin: 10px 0; display: flex; align-items: flex-start;'>")
                    .append("<img src='cid:productImage").append(productIndex1).append("' alt='Product Image' style='width: 80px; height: 100px; border: 1px solid #ccc; border-radius: 5px; margin-right: 10px;'/>")
                    .append("<div style='flex-grow: 1;'>")
                    .append("<h1 style='color: #007BFF; font-size: 18px; margin: 0;'>").append(productName).append("</h1>")
                    .append("<p style='margin: 5px 0;'><strong>Loại máy:</strong> ").append(productDescription).append("</p>")
                    .append("<p style='margin: 5px 0;'><strong>Giá:</strong> ").append(currencyFormat.formatCurrency(productPrice)).append("</p>")
                    .append("<p style='margin: 5px 0;'><strong>Số lượng:</strong> ").append(productQuantity).append("</p>")
                    .append("</div></div>");
            productIndex1 = String.valueOf(Integer.parseInt(productIndex1) + 1);
        }

// Phần tổng cộng
        content.append("<div style='text-align: right; margin-top: 20px;'>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Tổng giá trị sản phẩm: ").append(currencyFormat.formatCurrency(billDTO.getSubtotal())).append("</p>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Khuyến mãi: ").append(currencyFormat.formatCurrency(billDTO.getDiscount())).append("</p>")
                .append("<p style='font-weight: bold; font-size: 16px;'>Tổng cộng: ").append(currencyFormat.formatCurrency(billDTO.getTotal())).append("</p>")
                .append("</div>");

// Thiết lập nội dung email
        helper.setText(content.toString(), true);

// Đính kèm hình ảnh sản phẩm
        String productIndex2 = "1";
        for (CartItemDTO product : cartItemDTOS) {

            FileSystemResource image = new FileSystemResource(new File("E:/apache-tomcat-8.5.92/apache-tomcat-8.5.92/webapps/ChronoLuxWeb/template/web/img/products/" + product.getProductImgUrl()));
            helper.addInline("productImage" + productIndex2, image, "image/webp");
            productIndex2 = String.valueOf(Integer.parseInt(productIndex2) + 1);
        }

// Gửi email
        mailSender.send(message);
    }

    public static void sendEmailAsync(String recipientEmail, List<CartItemDTO> cartItems, BillDTO bill,
                               JavaMailSender mailSender, CurrencyFormat currencyFormat) {
        Thread t1 = new Thread  (() -> {
            try {
                MailConfig.sendEmail(recipientEmail, cartItems, bill, mailSender, currencyFormat);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
    }

    public static void sendEmailToAdminAsync(String adminEmail, List<CartItemDTO> cartItems, BillDTO bill,
                                      JavaMailSender mailSender, CurrencyFormat currencyFormat) {
        Thread t1 = new Thread (() -> {
            try {
                MailConfig.sendEmailToAdmin(adminEmail, cartItems, bill, mailSender, currencyFormat);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
    }
}
