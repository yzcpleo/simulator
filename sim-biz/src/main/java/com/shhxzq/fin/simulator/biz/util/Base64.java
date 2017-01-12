package com.shhxzq.fin.simulator.biz.util;

/**
 * @author kangyonggan
 * @since 16/6/2
 */
public class Base64 {
    private String mString;
    private int mIndex = 0;

    public Base64() {
    }

    public static String encode(byte[] data) {
        return (new Base64()).internalEncode(data);
    }

    public String internalEncode(byte[] data) {
        int charCount = data.length * 4 / 3 + 4;
        StringBuffer result = new StringBuffer(charCount * 77 / 76);
        int byteArrayLength = data.length;
        int byteArrayIndex = 0;

        byte b3;
        byte b2;
        byte b1;
        int var11;
        while (byteArrayIndex < byteArrayLength - 2) {
            var11 = this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            var11 <<= 8;
            var11 |= this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            var11 <<= 8;
            var11 |= this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            b3 = (byte) (63 & var11);
            var11 >>= 6;
            b2 = (byte) (63 & var11);
            var11 >>= 6;
            b1 = (byte) (63 & var11);
            var11 >>= 6;
            byte b11 = (byte) (63 & var11);
            result.append(this.mapByteToChar(b11));
            result.append(this.mapByteToChar(b1));
            result.append(this.mapByteToChar(b2));
            result.append(this.mapByteToChar(b3));
        }

        if (byteArrayIndex == byteArrayLength - 1) {
            var11 = this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            var11 <<= 4;
            b3 = (byte) (63 & var11);
            var11 >>= 6;
            b2 = (byte) (63 & var11);
            result.append(this.mapByteToChar(b2));
            result.append(this.mapByteToChar(b3));
            result.append("==");
        }

        if (byteArrayIndex == byteArrayLength - 2) {
            var11 = this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            var11 <<= 8;
            var11 |= this.convertUnsignedByteToInt(data[byteArrayIndex++]);
            var11 <<= 2;
            b3 = (byte) (63 & var11);
            var11 >>= 6;
            b2 = (byte) (63 & var11);
            var11 >>= 6;
            b1 = (byte) (63 & var11);
            result.append(this.mapByteToChar(b1));
            result.append(this.mapByteToChar(b2));
            result.append(this.mapByteToChar(b3));
            result.append("=");
        }

        return result.toString();
    }

    private int convertUnsignedByteToInt(byte b) {
        return b >= 0 ? b : 256 + b;
    }

    private char mapByteToChar(byte b) {
        if (b < 26) {
            return (char) (65 + b);
        } else if (b < 52) {
            return (char) (97 + (b - 26));
        } else if (b < 62) {
            return (char) (48 + (b - 52));
        } else if (b == 62) {
            return '+';
        } else if (b == 63) {
            return '/';
        } else {
            throw new IllegalArgumentException("Byte " + new Integer(b) + " is not a valid Base64 value");
        }
    }

    public static byte[] decode(String data) {
        return (new Base64()).internalDecode(data);
    }

    public byte[] internalDecode(String data) {
        this.mString = data;
        this.mIndex = 0;
        int mUsefulLength = 0;
        int length = this.mString.length();

        int byteArrayLength;
        for (byteArrayLength = 0; byteArrayLength < length; ++byteArrayLength) {
            if (this.isUsefulChar(this.mString.charAt(byteArrayLength))) {
                ++mUsefulLength;
            }
        }

        byteArrayLength = mUsefulLength * 3 / 4;
        byte[] result = new byte[byteArrayLength];

        int byteIndex;
        int var8;
        for (byteIndex = 0; byteIndex + 2 < byteArrayLength; byteIndex += 3) {
            var8 = this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            result[byteIndex + 2] = (byte) (var8 & 255);
            var8 >>= 8;
            result[byteIndex + 1] = (byte) (var8 & 255);
            var8 >>= 8;
            result[byteIndex] = (byte) (var8 & 255);
        }

        if (byteIndex == byteArrayLength - 1) {
            var8 = this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            var8 >>= 4;
            result[byteIndex] = (byte) (var8 & 255);
        }

        if (byteIndex == byteArrayLength - 2) {
            var8 = this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            var8 <<= 6;
            var8 |= this.mapCharToInt(this.getNextUsefulChar());
            var8 >>= 2;
            result[byteIndex + 1] = (byte) (var8 & 255);
            var8 >>= 8;
            result[byteIndex] = (byte) (var8 & 255);
        }

        return result;
    }

    private boolean isUsefulChar(char c) {
        return c >= 65 && c <= 90 || c >= 97 && c <= 122 || c >= 48 && c <= 57 || c == 43 || c == 47;
    }

    private char getNextUsefulChar() {
        char result;
        for (result = 95; !this.isUsefulChar(result); result = this.mString.charAt(this.mIndex++)) {
            ;
        }

        return result;
    }

    private int mapCharToInt(char c) {
        if (c >= 65 && c <= 90) {
            return c - 65;
        } else if (c >= 97 && c <= 122) {
            return c - 97 + 26;
        } else if (c >= 48 && c <= 57) {
            return c - 48 + 52;
        } else if (c == 43) {
            return 62;
        } else if (c == 47) {
            return 63;
        } else {
            throw new IllegalArgumentException(c + " is not a valid Base64 character.");
        }
    }
}
