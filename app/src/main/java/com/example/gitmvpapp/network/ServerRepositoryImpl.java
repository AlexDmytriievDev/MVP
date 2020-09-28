package com.example.gitmvpapp.network;

public class ServerRepositoryImpl implements ServerRepository {

    private final RestApi restApi;

    public ServerRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }
}
