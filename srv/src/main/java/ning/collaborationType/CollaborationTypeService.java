package ning.collaborationType;

import java.util.ArrayList;
import java.util.List;

import com.sap.cloud.sdk.service.prov.api.*;
import com.sap.cloud.sdk.service.prov.api.annotations.*;
import com.sap.cloud.sdk.service.prov.api.exits.*;
import com.sap.cloud.sdk.service.prov.api.request.*;
import com.sap.cloud.sdk.service.prov.api.response.*;
import org.slf4j.*;

public class CollaborationTypeService {

	private static final Logger LOG = LoggerFactory.getLogger(CollaborationTypeService.class.getName());

	@BeforeRead(entity = "CollaborationTypeMetadata", serviceName = "CollaborationType")
	public BeforeReadResponse beforeReadOrders(ReadRequest req, ExtensionHelper h) {
		LOG.error("##### CollaborationType - beforeRead ########");
		return BeforeReadResponse.setSuccess().response();
	}

	@AfterRead(entity = "CollaborationTypeMetadata", serviceName = "CollaborationType")
	public ReadResponse afterReadOrders(ReadRequest req, ReadResponseAccessor res, ExtensionHelper h) {
		EntityData ed = res.getEntityData();
		EntityData ex = EntityData.getBuilder(ed).addElement("amount", 10).buildEntityData("Orders");
		return ReadResponse.setSuccess().setData(ex).response();
	}

	@AfterQuery(entity = "CollaborationTypeMetadata", serviceName = "CollaborationType")
	public QueryResponse afterQueryOrders(QueryRequest req, QueryResponseAccessor res, ExtensionHelper h) {
		List<EntityData> dataList = res.getEntityDataList(); // original list
		List<EntityData> modifiedList = new ArrayList<EntityData>(dataList.size()); // modified list
		for (EntityData ed : dataList) {
			EntityData ex = EntityData.getBuilder(ed).addElement("amount", 10).buildEntityData("Orders");
			modifiedList.add(ex);
		}
		return QueryResponse.setSuccess().setData(modifiedList).response();
	}

}
