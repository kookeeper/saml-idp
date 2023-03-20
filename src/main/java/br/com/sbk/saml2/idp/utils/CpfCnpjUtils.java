package br.com.sbk.saml2.idp.utils;

public class CpfCnpjUtils {

	private static final int[] PESO_CPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] PESO_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static void main(final String[] args) {
		System.out.println(CpfCnpjUtils.isValidCPF("344.383.758-19"));
		System.out.println(CpfCnpjUtils.isValidCPF("215.134.648-09"));

		System.out.println(CpfCnpjUtils.isValidCNPJ("11.111.111/1111-11"));

		System.out.println(CpfCnpjUtils.isValidCNPJ("24.072.116/0001-02"));
		System.out.println(CpfCnpjUtils.isValidCNPJ("14.402.730/0001-23"));
	}

	public static boolean isValid(final String cpfCnpj) {
		return CpfCnpjUtils.isValidCPF(cpfCnpj) || CpfCnpjUtils.isValidCNPJ(cpfCnpj);
	}

	public static boolean isValidCPF(final String input) {
		if (input == null) {
			return false;
		}
		final String cpf = CpfCnpjUtils.padLeft(input.trim().replace(".", "").replace("-", ""), 11, '0');
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333") || cpf.equals("44444444444")
				|| cpf.equals("55555555555") || cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")
				|| cpf.length() != 11) {
			return false;
		}
		// Calculate and check
		final Integer digito1 = CpfCnpjUtils.calcularDigito(cpf.substring(0, 9), CpfCnpjUtils.PESO_CPF);
		final Integer digito2 = CpfCnpjUtils.calcularDigito(cpf.substring(0, 9) + digito1, CpfCnpjUtils.PESO_CPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCNPJ(final String input) {
		if (input == null) {
			return false;
		}
		final String cnpj = CpfCnpjUtils.padLeft(input.trim().replace(".", "").replace("-", "").replace("/", ""), 14, '0');
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222") || cnpj.equals("33333333333333") || cnpj.equals("44444444444444")
				|| cnpj.equals("55555555555555") || cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888") || cnpj.equals("99999999999999")
				|| cnpj.length() != 14) {
			return false;
		}
		// Calculate and check
		final Integer digito1 = CpfCnpjUtils.calcularDigito(cnpj.substring(0, 12), CpfCnpjUtils.PESO_CNPJ);
		final Integer digito2 = CpfCnpjUtils.calcularDigito(cnpj.substring(0, 12) + digito1, CpfCnpjUtils.PESO_CNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	private static int calcularDigito(final String input, final int[] peso) {
		int soma = 0;
		for (int indice = input.length() - 1, digito; indice >= 0; indice--) {
			// converte o i-esimo caractere do CPF em um numero:
			// por exemplo, transforma o caractere '0' no inteiro 0. (48 eh a posicao de '0' na tabela ASCII)
			digito = input.charAt(indice) - 48;
			soma = soma + digito * peso[peso.length - input.length() + indice];
		}
		final int resto = soma * 10 % 11;
		return resto > 9 ? 0 : resto;
	}

	private static String padLeft(final String text, final int size, final char character) {
		return String.format("%" + size + "s", text).replace(' ', character);
	}

}
