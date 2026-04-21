package com.example.backend.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

/**
 * Googleから送られてきたIDトークンが正当なものか検証するユーティリティクラス
 */
public class GoogleTokenVerifier {

    // Google Cloud Consoleで取得した「クライアントID」をここにセット
    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com";
    // 通信とJSON変換用のインスタンスは再利用するのが推奨されます
    private static final NetHttpTransport TRANSPORT = new NetHttpTransport();
    private static final GsonFactory GSON_FACTORY = new GsonFactory();

    /**
     * IDトークン文字列を検証し、成功すれば中身（Payload）を返します。
     * * @param idTokenString フロントエンドから送られてくるJWT
     * @return 検証済みのPayload（名前、メールアドレス、識別ID等を含む）
     * @throws Exception 検証に失敗した場合や通信エラー
     */
    public static Payload verify(String idTokenString) throws Exception {

        // 1. 検証器（Verifier）を構築
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(TRANSPORT, GSON_FACTORY)
                // このクライアントID向けに発行されたトークンであることを確認
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        // 2. トークンを検証
        // 署名の検証、有効期限のチェック、発行元の確認（accounts.google.com）を自動で行います
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