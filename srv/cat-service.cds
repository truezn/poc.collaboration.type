using ning.CollaborationType as my from '../db/CollaborationTypeMetadata';
using ning.CollaborationTypePurpose as purpose from '../db/CollaborationTypePurpose';
service CollaborationType {
    entity CollaborationTypeMetadata as projection on my.CollaborationTypeMetadata;
    action setToUse(collaborationTypeId:String, state:Integer, version:String);
    entity CollaborationTypePurpose as projection on purpose.CollaborationTypePurpose;
}
