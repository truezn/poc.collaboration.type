package poc.collaborationtype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import org.slf4j.Logger;

import com.sap.cloud.sdk.service.prov.api.annotations.Action;
import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.operations.Query;

import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;

public class CollaborationType {

	@Create(serviceName = "CollaborationType", entity = "CollaborationTypeMetadata")
	public CreateResponse addCollaborationTypeMetadata(CreateRequest createRequest, ExtensionHelper extensionHelper) {

		// logger.info("Creating Customer");
		DataSourceHandler handler = extensionHelper.getHandler();

		EntityData entityData = createRequest.getData();
		Integer state = 1;
		// set default
		entityData = EntityData.getBuilder(entityData).removeElement("state").addElement("state", state)
				.buildEntityData("CollaborationTypeMetadata");
		/*
		 * The executeInsert method takes two parameters: 1. entityData containing the
		 * current request data 2. a boolean value that indicates whether the created
		 * entity needs to be returned. If true, the handler would internally make
		 * another GET call and return the created entity as entityData. Here we give
		 * this value as true since we need to return it back in the CreateResponse.
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
			EntityData entityData = handler.executeRead("CollaborationTypeMetadata", keys,
					Arrays.asList("collaborationTypeId", "version"));
			// EntityData entityData = new DefaultEntityData();
			Integer state = 3;
			// update the state value
			entityData = EntityData.getBuilder(entityData).removeElement("state").addElement("state", state)
					.buildEntityData("CollaborationTypeMetadata");
			// updating the CollaborationTypeMetadata
			handler.executeUpdate(entityData, keys, false);

			OperationResponse response = OperationResponse.setSuccess().setEntityData(Arrays.asList(entityData))
					.response();

			return response;
		} catch (DatasourceException e) {
			// Return an instance of OperationResponse containing the error in
			// case of failure
			ErrorResponse errorResponse = ErrorResponse.getBuilder().setMessage(e.getMessage()).setCause(e).response();

			return OperationResponse.setError(errorResponse);
		}

	}

}
