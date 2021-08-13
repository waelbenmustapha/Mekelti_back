package com.isima.mekelti.Service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isima.mekelti.Repositroy.ChefRepository;
import com.isima.mekelti.Repositroy.ShopRepository;
import com.isima.mekelti.Repositroy.UtilisateurRepository;
import com.isima.mekelti.entity.Chef;
import com.isima.mekelti.entity.Shop;
import com.isima.mekelti.entity.Utilisateur;

import net.bytebuddy.utility.RandomString;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChefService {
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ChefRepository chefRepository;
  private final UtilisateurRepository utilisateurRepository;
  private final ShopRepository shopRepository;
  private JavaMailSender mailSender;


  public String signup(Chef chef) throws UnsupportedEncodingException, MessagingException {
    boolean exists =  utilisateurRepository.findByEmail(chef.getEmail()).isPresent();
    if(exists){
      throw new IllegalStateException("email already exists");
    }
    System.out.println("jsp"+chef.getNom());
    String encodedPassword =  bCryptPasswordEncoder.encode(chef.getMotDePasse());
    String generatedverifcationcode = RandomString.make(64);

    chef.setVerificationCode(generatedverifcationcode);
    chef.setMotDePasse(encodedPassword);
    String token = RandomString.make(64);
    chef.setToken(token);
    Shop shop = new Shop();
    shop.setLatitude(chef.getShop().getLatitude());
    shop.setLongitude(chef.getShop().getLongitude());
    shop.setLivraison(chef.getShop().getLivraison());
    shop.setNom(chef.getShop().getNom());
    shop.setType(chef.getShop().getType());
shopRepository.save(shop);
    chef.setShop(shop);
    sendVerificationEmail(chef);
    chefRepository.save(chef);
shop.setChef(chef);
shopRepository.save(shop);
    return "it works signup encoded";
  }


  private void sendVerificationEmail(Utilisateur user)
      throws MessagingException, UnsupportedEncodingException {
    String toAddress = user.getEmail();
    String link = "http://192.168.1.60:8095/Authentification/verify/" + user.getVerificationCode();
    String fromAddress = "mekelti@application.tn";
    String senderName = "Mekelti";
    String username = user.getNom();
    String subject = "Please verify your registration";
    String content = emailform(username, link);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);


    helper.setText(content, true);

    mailSender.send(message);

    System.out.println("Email has been sent");

  }
  public Boolean matchPasswords(String rawPassword, String encodedPassword) {
    return (bCryptPasswordEncoder.matches(rawPassword, encodedPassword));
  }
  public boolean verify(String verificationCode) {

    Utilisateur user = utilisateurRepository.findByVerificationCode(verificationCode);

    if (user == null || user.getEnabled()==true) {
      return false;
    } else {
      user.setVerificationCode(null);
      user.setEnabled(true);
      utilisateurRepository.save(user);

      return true;
    }

  } private String emailform(String name, String link) {
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
        "\n" +
        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
        "\n" +
        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
        "        \n" +
        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
        "          <tbody><tr>\n" +
        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td style=\"padding-left:10px\">\n" +
        "                  \n" +
        "                    </td>\n" +
        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "              </a>\n" +
        "            </td>\n" +
        "          </tr>\n" +
        "        </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
        "      <td>\n" +
        "        \n" +
        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "\n" +
        "\n" +
        "\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
        "        \n" +
        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
        "\n" +
        "</div></div>";
  }
}
