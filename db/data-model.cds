namespace poc.CollaborationType;

using ning.CollaborationTypePurpose.CollaborationTypePurpose from './CollaborationTypePurpose';

entity CollaborationTypeMetadata {
  key collaborationTypeId : String;
  key version : String;
      createdOn : Timestamp; 
      state: Integer;
      statusProfile: String;
      changedOn: Timestamp;
      project : String;
      purpose : Association to many CollaborationTypePurpose on  purpose.collaborationTypeId = $self.collaborationTypeId
    														 and purpose.version  = $self.version
    														 and purpose.language = 'EN';
      description : Association to many CollaborationTypeDescription on description.collaborationTypeId = $self.collaborationTypeId
    																and description.version = $self.version
    																and description.language = 'EN'
      
}

entity CollaborationTypePurpose {
	
	key collaborationTypeId:String;
	key version:String;
	key language:String;
	    purpose:String;
}

entity CollaborationTypeDescription {
	key collaborationTypeId : String;
	key version : Integer; 
	key language : String;
	    descripton : String;
}
