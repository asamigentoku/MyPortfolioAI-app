package com.example.backend.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Googleから送られてきたIDトークンが正当なものか検証するユーティリティクラス
 */
@Component // ← SpringのDIコンテナに登録
public class GoogleTokenVerifier {

    // staticをやめて、インスタンスフィールドに変更
    private final String clientId;
    private final NetHttpTransport transport;
    private final GsonFactory gsonFactory;

    // コンストラクタでSpringが自動的に値を注入してくれる
    public GoogleTokenVerifier(@Value("${google.client-id}") String clientId) {
        this.clientId = clientId;
        this.transport = new NetHttpTransport();
        this.gsonFactory = new GsonFactory();
    }

    /**
     * IDトークン文字列を検証し、成功すれば中身（Payload）を返します。
     * @param idTokenString フロントエンドから送られてくるJWT
     * @return 検証済みのPayload（名前、メールアドレス、識別ID等を含む）
     * @throws Exception 検証に失敗した場合や通信エラー
     */
    public Payload verify(String idTokenString) throws Exception { // ← staticを削除

        // 1. 検証器（Verifier）を構築
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, gsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        // 2. トークンを検証
        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            // 3. 検証成功。中身のユーザー情報を返す
            return idToken.getPayload();
        } else {
            // 4. 検証失敗（トークンが偽造されている、または期限切れ）
            throw new RuntimeException("Invalid ID token.");
        }
    }
}