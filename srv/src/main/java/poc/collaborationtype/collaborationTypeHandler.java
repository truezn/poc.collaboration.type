package poc.collaborationtype;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.sap.cloud.sdk.service.prov.api.annotations.Action;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.response.ErrorResponse;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;

public class collaborationTypeHandler {

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
