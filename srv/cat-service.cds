using poc.CollaborationType as db from '../db/data-model';
using API_BUSINESS_PARTNER as bp from '../srv/external/csn/ODataServiceforBusinessPartner';
using API_ENTERPRISE_PROJECT_SRV as ent from '../srv/external/csn/EnterpriseProjectCreateReadUpdateDelete';
service CollaborationType {
    entity CollaborationTypeMetadata as projection on db.CollaborationTypeMetadata;
    action setToUse(collaborationTypeId:String, state:Integer, version:String);
    entity CollaborationTypePurpose as projection on db.CollaborationTypePurpose;
    entity Suppliers as projection on bp.A_SupplierType
	   excluding {to_SupplierCompany, to_SupplierPurchasingOrg};
	entity EnterpriseProjects as projection on ent.A_EnterpriseProjectType
	   excluding {to_EnterpriseProjectElement, to_EnterpriseProjectJVA};
    
}
