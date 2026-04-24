package Resources;

import javax.swing.text.*;

public class IdCardFilter extends DocumentFilter {

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

        // simular texto final
        StringBuilder sb = new StringBuilder(current);
        sb.replace(offset, offset + length, text);

        // solo números
        String digits = sb.toString().replaceAll("\\D", "");

        // máximo 9 dígitos
        if (digits.length() > 9) return;

        // formatear
        String formatted = formatId(digits);

        fb.replace(0, doc.getLength(), formatted, attrs);
    }

    private String formatId(String digits) {
        if (digits.length() <= 1) return digits;
        if (digits.length() <= 5) return digits.substring(0, 1) + "-" + digits.substring(1);
        return digits.substring(0, 1) + "-" +
               digits.substring(1, 5) + "-" +
               digits.substring(5);
    }
}
