using my.Collaboration as my from '../db/CollaborationTypeMetadata';


service Collaboration {
    @readonly entity Collaboration as projection on my.CollaborationTypeMetadata;
}
