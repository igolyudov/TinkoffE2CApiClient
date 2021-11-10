package ru.tinkoff.crypto.mapi;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Map;
import java.util.stream.Collectors;
import ru.CryptoPro.JCP.JCP;

/**
 * Класс предоставляет операции для формирования подписи для MAPI.
 *
 * @author a.bloshchetsov
 */
public final class CryptoMapi {

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
     * @param digestAlgorithmName алгоритм хеширования, возможные значения {@link JCP#GOST_DIGEST_NAME},
     *                            {@link JCP#GOST_DIGEST_2012_256_NAME}, {@link JCP#GOST_DIGEST_2012_512_NAME}.
     * @return вычисленное хеш-значение.
     * @throws NoSuchAlgorithmException в случае если необходимый алгоритм не был найден в окружении.
     */
    public final byte[] calcDigest(final String digestAlgorithmName, final byte[] data) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithmName);
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Метод остался для поддержки обратной совместимости и по умолчанию использует {@link JCP#GOST_DIGEST_NAME} алгоритм, поддержка которого
     * завершится после 1 января 2019 года.
     *
     * @deprecated необходимо использовать метод {@link CryptoMapi#calcDigest(String, byte[])} с указанием конкретного алгоритма.
     */
    @Deprecated
    public final byte[] calcDigest(final byte[] data) throws NoSuchAlgorithmException {
        return calcDigest(JCP.GOST_DIGEST_NAME, data);
    }

    /**
     * Формирует подпись для переданных данных.
     *
     * @param signAlgorithmName алгоритм подписи, возможные значения {@link JCP#GOST_EL_SIGN_NAME},
     *                          {@link JCP#GOST_EL_2012_256_NAME}, {@link JCP#GOST_EL_2012_512_NAME}.
     * @param key               ключ для формирования подписи.
     * @param data              сырые данные.
     * @return сформированную подпись.
     * @throws NoSuchProviderException  в случае если необходимый пройвайдер не был найден в окружении.
     * @throws NoSuchAlgorithmException в случае если необходимый алгоритм не был найден в окружении.
     * @throws InvalidKeyException      в случае если передан неправильный закрытуый ключ.
     * @throws SignatureException       в случае если при формировании подписи произошла ошибка.
     */
    public final byte[] calcSignature(final String signAlgorithmName, final PrivateKey key, final byte[] data) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        final Signature signature = Signature.getInstance(signAlgorithmName, JCP.PROVIDER_NAME);
        signature.initSign(key);
        signature.update(data);
        return signature.sign();
    }

    /**
     * Метод остался для поддержки обратной совместимости и по умолчанию использует {@link JCP#GOST_EL_SIGN_NAME} алгоритм, поддержка которого
     * завершится после 1 января 2019 года.
     *
     * @deprecated необходимо использовать метод {@link CryptoMapi#calcSignature(String, PrivateKey, byte[])} с указанием конкретного алгоритма.
     */
    @Deprecated
    public final byte[] calcSignature(final PrivateKey key, final byte[] data) throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        return calcSignature(JCP.GOST_EL_SIGN_NAME, key, data);
    }

}
