using ning.CollaborationType as my from '../db/CollaborationTypeMetadata';
using ning.CollaborationTypePurpose as purpose from '../db/CollaborationTypePurpose';
using API_ENTERPRISE_PROJECT_SRV as enterproject from   './external/csn/EnterpriseProjectCreateReadUpdateDelete';
service CollaborationType {
    entity CollaborationTypeMetadata as projection on my.CollaborationTypeMetadata;
    action setToUse(collaborationTypeId:String, state:Integer, version:String);
    entity CollaborationTypePurpose as projection on purpose.CollaborationTypePurpose;
    entity EnterpriseProjects       as projection on enterproject.A_EnterpriseProjectType
                                       excluding {to_EnterpriseProjectElement, to_EnterpriseProjectJVA};
}
