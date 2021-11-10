package ru.tinkoff.crypto.mapi;

import java.security.*;
import java.util.Map;
import java.util.stream.Collectors;

public class RsaCryptoMapi {
    /**
     * Формирует канонизированную строку из переданных данных.
     *
     * @param data параметры.
     * @return канонизированную строку.
     */
    public final String concatValues(final Map<String, String> data) {
        return data.keySet().stream().sorted().map(data::get).collect(Collectors.joining());
    }

    /**
     * Вычисляет хеш-значение от переданных данных.
     *
     * @param data                сырые данные.
     * @return вычисленное хеш-значение.
     * @throws NoSuchAlgorithmException в случае если необходимый алгоритм не был найден в окружении.
     */
    public final byte[] calcDigest(final byte[] data) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Формирует подпись для переданных данных.
     *
     * @param key               ключ для формирования подписи.
     * @param data              сырые данные.
     * @return сформированную подпись.
     * @throws NoSuchAlgorithmException в случае если необходимый алгоритм не был найден в окружении.
     * @throws InvalidKeyException      в случае если передан неправильный закрытуый ключ.
     * @throws SignatureException       в случае если при формировании подписи произошла ошибка.
     */
    public final byte[] calcSignature(final PrivateKey key, final byte[] data) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        final Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(data);
        return signature.sign();
    }
}
