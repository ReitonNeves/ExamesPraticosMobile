package br.gov.ma.detran.examespraticosmobile.util;

import android.util.Base64;
import android.util.Log;

import com.auth0.android.jwt.JWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

public class TokenUtil {

    public static String createJWT(String id, String issuer, String audience, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(new ConstantesUtil(ConstantesUtil.Ambiente.DESENVOLVIMENTO).SALT_TOKEN);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(HS256, apiKeySecretBytes);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static String decodeJWT(String token) throws Exception {

        JWT jwt = new JWT(token);
        return jwt.getAudience().get(0);
        /*Claims body = null;
        try {
            body = Jwts.parser().setSigningKey("detran").parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //return decoded(token);

        /*//byte[] apiKeySecretBytes = Base64.decode(new ConstantesUtil().SALT_TOKEN, URL_SAFE);
        //return new String(bytes, "UTF-8");

        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(new ConstantesUtil().SALT_TOKEN);

        //Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        Claims claim = Jwts.parser()
                .setSigningKey("detran")
                .parseClaimsJws(token.trim()).getBody();

        return claim.getAudience();*/

    }

    public static void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
        } catch (UnsupportedEncodingException e) {
            //Error
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }


}
