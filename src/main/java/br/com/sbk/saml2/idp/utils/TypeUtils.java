package br.com.sbk.saml2.idp.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeUtils {

	public static String readPositionalAsString(final StringBuilder sb, final Integer tamanho) {
		final String retorno = sb.substring(0, tamanho).trim();
		sb.delete(0, tamanho);
		return retorno;
	}

	public static Integer readPositionalAsInteger(final StringBuilder sb, final Integer tamanho) {
		final String retorno = sb.substring(0, tamanho).trim();
		sb.delete(0, tamanho);
		if (retorno.isEmpty()) {
			return null;
		}
		return Integer.valueOf(retorno);
	}

	public static Long readPositionalAsLong(final StringBuilder sb, final Integer tamanho) {
		final String retorno = sb.substring(0, tamanho).trim();
		sb.delete(0, tamanho);
		if (retorno.isEmpty()) {
			return null;
		}
		return Long.valueOf(retorno);
	}

	public static Double readPositionalAsDouble(final StringBuilder sb, final Integer tamanho) {
		final String retorno = sb.substring(0, tamanho).trim();
		sb.delete(0, tamanho);
		if (retorno.isEmpty()) {
			return null;
		}
		return Double.valueOf(retorno);
	}

	public static Date readPositionalAsDate(final StringBuilder sb, final Integer tamanho) throws ParseException {
		return TypeUtils.readPositionalAsDate(sb, tamanho, "dd/MM/yy");
	}

	public static Date readPositionalAsDate(final StringBuilder sb, final Integer tamanho, final String format) throws ParseException {
		final String stringDate = sb.substring(0, tamanho).trim();
		sb.delete(0, tamanho);
		return TypeUtils.stringToDate(stringDate, format);
	}

	private static Date stringToDate(final String stringDate, final String format) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		if (!stringDate.isEmpty()) {
			return new Date(simpleDateFormat.parse(stringDate).getTime());
		}
		return null;
	}

}
