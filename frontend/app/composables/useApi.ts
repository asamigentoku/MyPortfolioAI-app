// composables/useApi.ts
import createClient from "openapi-fetch";
import type { paths } from "../pages/types/openapi"; // 生成した型定義のパス

export const useApi = () => {
    const config = useRuntimeConfig();

    // クライアントの初期化
    // 必要に応じて baseUrl を runtimeConfig から取得するように変更してください
    const client = createClient<paths>({
        baseUrl: "http://localhost:8080",
    });

    return client;
};