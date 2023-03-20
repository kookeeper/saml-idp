package br.com.sbk.saml2.idp.common.jpa;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;

/**
 * Classe pra converter localDate para date.
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
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

   /**
    * Bean {@link LocalDate}
    * 
    * @return {@link Date}
    */
   @Override
   public Date convertToDatabaseColumn(LocalDate locDate) {
      return locDate == null ? null : Date.valueOf(locDate);
   }

   /**
    * Bean {@link Date}
    * 
    * @return {@link LocalDate}
    */
   @Override
   public LocalDate convertToEntityAttribute(Date sqlDate) {
      return sqlDate == null ? null : sqlDate.toLocalDate();
   }

}