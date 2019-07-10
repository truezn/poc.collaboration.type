using ning.CollaborationType as my from '../db/CollaborationTypeMetadata';


service CollaborationType {
    entity CollaborationTypeMetadata as projection on my.CollaborationTypeMetadata;
}
