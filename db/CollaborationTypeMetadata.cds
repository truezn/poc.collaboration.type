namespace my.Collaboration;

entity CollaborationTypeMetadata {
  key collaborationTypeId : String;
  key version : String;
      createdOn : Timestamp; 
      state: Integer;
      statusProfile: String;
      changedOn: Timestamp;
}
