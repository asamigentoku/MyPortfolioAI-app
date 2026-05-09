import createClient from "openapi-fetch";
import type { paths } from "../pages/types/openapi";

export const useApi = () => {
    const auth = useAuthStore()

    const client = createClient<paths>({
        baseUrl: "http://localhost:8080",
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