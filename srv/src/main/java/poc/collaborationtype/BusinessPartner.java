package poc.collaborationtype;

import java.util.*;

import com.sap.cloud.sdk.service.prov.api.annotations.*;
import com.sap.cloud.sdk.service.prov.api.*;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.request.*;
import com.sap.cloud.sdk.service.prov.api.response.*;
import com.sap.cloud.sdk.odatav2.connectivity.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sdk.s4hana.connectivity.*;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.Supplier;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

public class BusinessPartner {

	private static final String DESTINATION_NAME = "APIHubBP";
	private static final String apikey = "dJ6uFzfpbYFvTB3AUz2H1wL4AURielDX";
	private ErpConfigContext context = new ErpConfigContext(DESTINATION_NAME);

	@Query(serviceName = "CollaborationType", entity = "Suppliers")
	public QueryResponse querySupplier(QueryRequest qryRequest) {

		QueryResponse queryResponse;
		int top = qryRequest.getTopOptionValue();
		int skip = qryRequest.getSkipOptionValue();

		try {
			// Create Map containing request header information
			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("Content-Type", "application/json");
			requestHeaders.put("APIKey", apikey);

			final List<Supplier> suppliers = new DefaultBusinessPartnerService().getAllSupplier()
					.withCustomHttpHeaders(requestHeaders).onRequestAndImplicitRequests()
					.select(Supplier.SUPPLIER, Supplier.SUPPLIER_NAME).top(top >= 0 ? top : 50)
					.skip(skip >= 0 ? skip : -1).execute(context);
			queryResponse = QueryResponse.setSuccess().setData(suppliers).response();

		} catch (final ODataException e) {
			ErrorResponse er = ErrorResponse.getBuilder()
					.setMessage("Error occurred with the Query operation: " + e.getMessage()).setStatusCode(500)
					.setCause(e).response();
			queryResponse = QueryResponse.setError(er);
		}

		return queryResponse;
	}

}
