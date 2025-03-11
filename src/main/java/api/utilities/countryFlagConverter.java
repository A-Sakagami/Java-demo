package api.utilities;

public class countryFlagConverter {
    public static String getFlagEmoji(String countryCode) {
        if (countryCode == null || countryCode.length() != 2) {
            return "🏳"; // 不明な場合は白旗を返す
        }
        StringBuilder flagEmoji = new StringBuilder();
        for (char c : countryCode.toUpperCase().toCharArray()) {
            flagEmoji.appendCodePoint(c - 'A' + 0x1F1E6);
        }
        return flagEmoji.toString();
    }
}

