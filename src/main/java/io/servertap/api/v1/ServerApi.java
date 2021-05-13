package io.servertap.api.v1;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import io.servertap.Lag;
import io.servertap.api.v1.models.Server;
import org.bukkit.Bukkit;

import java.lang.management.ManagementFactory;

public class ServerApi {

    @OpenApi(
            path = "/v1/server",
            summary = "Get information about the server",
            tags = {"Server"},
            headers = {
                    @OpenApiParam(name = "key")
            },
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent(from = Server.class))
            }
    )
    public static void serverGet(Context ctx) {
        Server server = new Server();

        server.setName(Bukkit.getServer().getName());
        server.setMotd(Bukkit.getServer().getMotd());
        server.setVersion(Bukkit.getServer().getBukkitVersion());
        server.setOnline(Bukkit.getOnlinePlayers().size());
        server.setTps(Lag.getTPSString());
        server.setCpus(Runtime.getRuntime().availableProcessors());
        server.setUptime(ManagementFactory.getRuntimeMXBean().getUptime());
        server.setMaxMemory(Runtime.getRuntime().maxMemory());
        server.setTotalMemory(Runtime.getRuntime().totalMemory());
        server.setFreeMemory(Runtime.getRuntime().freeMemory());

        ctx.json(server);
    }

}
