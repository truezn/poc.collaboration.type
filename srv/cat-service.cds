using poc.CollaborationType as db from '../db/data-model';

service CollaborationType {
    entity CollaborationTypeMetadata as projection on db.CollaborationTypeMetadata;
    action setToUse(collaborationTypeId:String, state:Integer, version:String);
    entity CollaborationTypePurpose as projection on db.CollaborationTypePurpose;
}
