package Resources;

import javax.swing.text.*;

public class PhoneFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        Document doc = fb.getDocument();
        String current = doc.getText(0, doc.getLength());

        StringBuilder sb = new StringBuilder(current);
        sb.replace(offset, offset + length, text);

        // quitar todo lo que no sea número
        String digits = sb.toString().replaceAll("\\D", "");

        // limitar a 8 dígitos
        if (digits.length() > 8) return;

        // aplicar formato
        String formatted = formatPhone(digits);

        fb.replace(0, doc.getLength(), formatted, attrs);
    }

    private String formatPhone(String digits) {
        if (digits.length() <= 4) return digits;
        return digits.substring(0, 4) + "-" + digits.substring(4);
    }
}
