package poc.collaborationtype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.sap.cloud.sdk.service.prov.api.annotations.Action;
import com.sap.cloud.sdk.service.prov.api.annotations.Function;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.it.commons.logging.slf4j.LoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;

import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultEnterpriseProjectService;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.enterpriseproject.EnterpriseProject;



public class collaborationTypeHandler {
	
	
//	Logger logger = LoggerFactory.getLogger(S4BookshopService.class);

	private static final String DESTINATION_NAME = "APIHubProject"; //Refers to the destination created in Step 6
	private static final String apikey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"; //Replace with your API key

	private ErpConfigContext context = new ErpConfigContext(DESTINATION_NAME);
//
	@Query(serviceName = "CollaborationType", entity = "EnterpriseProjectType")
	public QueryResponse queryEnterpriseProjectType(QueryRequest queryRequest) {
		QueryResponse queryResponse;
	    int top = queryRequest.getTopOptionValue();
	    int skip = queryRequest.getSkipOptionValue();

	    try {
	    	// Create Map containing request header information
	        	Map<String, String> requestHeaders = new HashMap<>();
	        	requestHeaders.put("Content-Type","application/json");
	        	requestHeaders.put("APIKey",apikey);

	    	final List<EnterpriseProject> enterpriseProject =
	    	             new DefaultEnterpriseProjectService().getAllEnterpriseProject()
	    	            .withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
	    	            .select(EnterpriseProject.ALL_FIELDS)
	    	            .top(top >= 0 ? top : 50)
	    	            .skip(skip >= 0 ? skip : -1)
	    	           .execute(context);
	    	queryResponse = QueryResponse.setSuccess().setData(enterpriseProject).response();

	    } catch (final ODataException e) {
//	    	logger.error("Error occurred with the Query operation: " + e.getMessage(), e);
	    	ErrorResponse er = ErrorResponse.getBuilder()
	    	                            .setMessage("Error occurred with the Query operation: " + e.getMessage())
	    	                            .setStatusCode(500).setCause(e).response();
	    	queryResponse = QueryResponse.setError(er);
	    }

		  return queryResponse;
	}
	
	@Create(serviceName = "CollaborationType", entity = "CollaborationTypeMetadata")
	public CreateResponse addCustomer(CreateRequest createRequest, ExtensionHelper extensionHelper) {

//	    logger.info("Creating Customer");
	    DataSourceHandler handler = extensionHelper.getHandler();

	    EntityData entityData = createRequest.getData();
	    Integer state = 1; 
//	    set default 
	    entityData = EntityData.getBuilder(entityData).removeElement("state").addElement("state", state).buildEntityData("CollaborationTypeMetadata");
	    /* The executeInsert method takes two parameters:
	     *  1. entityData containing the current request data
	     *  2. a boolean value that indicates whether the created entity needs to be returned. If true,
	     *    the handler would internally make another GET call and return the created entity as entityData.
	     *    Here we give this value as true since we need to return it back in the CreateResponse.
	     */
	    try {
	    	
	    	EntityData createdEntity = handler.executeInsert(entityData, true);
	    	CreateResponse createResponse = CreateResponse.setSuccess().setData(createdEntity).response();
		    return createResponse;

	    } catch (DatasourceException e) {
			// Return an instance of OperationResponse containing the error in
			// case of failure
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage(e.getMessage()).setCause(e).response();
			return CreateResponse.setError(errorResponse);
		}
	    

	}

	@Action(Name = "setToUse", serviceName = "CollaborationType")
	public OperationResponse setToUse(OperationRequest actionRequest, ExtensionHelper extensionHelper) {
		Map<String, Object> parameters = actionRequest.getParameters();
		DataSourceHandler handler = extensionHelper.getHandler();

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("collaborationTypeId", String.valueOf(parameters.get("collaborationTypeId")));
		keys.put("version", String.valueOf(parameters.get("version")));
		// fetching the collaborationType details for the is and version
		try {
			EntityData entityData = handler.executeRead("CollaborationTypeMetadata", keys, Arrays.asList("collaborationTypeId", "version"));
//			EntityData entityData = new DefaultEntityData();
		Integer state = 3; 
//	    update the state value 
	    entityData = EntityData.getBuilder(entityData).removeElement("state").addElement("state", state).buildEntityData("CollaborationTypeMetadata");
//	    updating the CollaborationTypeMetadata
	    handler.executeUpdate(entityData, keys, false);
	    
			OperationResponse response = OperationResponse.setSuccess().setEntityData(Arrays.asList(entityData)).response();

			return response;
		} catch (DatasourceException e) {
			// Return an instance of OperationResponse containing the error in
			// case of failure
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage(e.getMessage()).setCause(e).response();

			return OperationResponse.setError(errorResponse);
		}

	}

}
