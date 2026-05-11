import createClient from "openapi-fetch";
import type { paths } from "../pages/types/openapi";

export const useApi = () => {
    const auth = useAuthStore()
    const { public: { apiBaseUrl } } = useRuntimeConfig()

    const client = createClient<paths>({
        baseUrl: apiBaseUrl,
    });

    client.use({
        async onRequest({ request }) {
            const token = auth.user?.token
            if (token) {
                request.headers.set('Authorization', `Bearer ${token}`)
            }
            return request
        },
    })

    return client;
};