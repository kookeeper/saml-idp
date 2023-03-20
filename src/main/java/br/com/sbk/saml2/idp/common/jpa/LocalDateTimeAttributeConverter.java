package br.com.sbk.saml2.idp.common.jpa;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;

/**
 * Classe pra converter localDateTime para timestamp.
 * 
 * A vers�o dpo JPA ainda n�o suporta essa implementação.
 * 
 * Utilizar a classe do pacote dados-corporativos
 *
 * @author "TC011758 - Mauricio Cruz"
 * @version 1.0.0
 * @since 1.0.0
 *
 */
@Deprecated
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

   /**
    * Bean {@link LocalDateTime}
    * 
    * @return {@link Timestamp}
    */
   @Override
   public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
      return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
   }

   /**
    * Bean {@link Timestamp}
    * 
    * @return {@link LocalDateTime}
    */
   @Override
   public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
      return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
   }

}