package Core;

import io.restassured.response.Response;

public class ApiExecutor {


    private ApiBuilder api= null;

    private Response apiResponse = null;



    public ApiExecutor (ApiRequest request){

        ApiBuilder builder = new ApiBuilder();

        builder.setMethod(request.methodType());
        builder.getRequestSpecBuilder().setBasePath(request.basePath());
        builder.getRequestSpecBuilder().setBaseUri(request.baseURL());
        builder.getRequestSpecBuilder().addHeaders(request.headers());
        builder.getRequestSpecBuilder().setContentType(request.contentType());
        if(request.body() != null){
            builder.getRequestSpecBuilder().setBody(request.body());
        }
        builder.getRequestSpecBuilder().addQueryParams(request.queryParams());

        // Call Filter Implementations here

        this.api = builder;

    }


    public ApiResponse execute() {
        this.apiResponse = this.api.execute();
        return new ApiResponse(this.apiResponse);
    }




}
