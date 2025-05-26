package com.epam.dalvaradoc.mod2_spring_core_task.utils;

public class SwaggerExamples {
    private SwaggerExamples() {
    }

    public static final String GENERAL_AUTH_REQBODY = """
            {
                "username": "Peggie.Barthelemy",
                "password": "rD4=ob.G8"
            }
            """;

    public static final String GENERAL_CHANGE_PASSWORD_REQBODY = """
            {
                "username": "john.doe",
                "password": "currentPassword",
                "newPassword": "newPassword123"
            }
            """;

    public static final String GENERAL_CHANGE_PASSWORD_RESBODY = """
            {
                "username": "john.doe",
                "password": "newPassword123"
            }
            """;

    public static final String TRAINEE_REGISTER_REQBODY = """
            {
                "firstName": "Peggie",
                "lastName": "Barthelemy",
                "birthdate": "1953-08-14",
                "address": "666 Dunning Pass",
                "active": false
            }
            """;

    public static final String TRAINEE_GET_RESBODY = """
            {
                "firstName": "Peggie",
                "lastName": "Barthelemy",
                "isActive": false,
                "birthdate": "1953-08-14",
                "address": "666 Dunning Pass",
                "trainers": [
                    {
                        "auth": {
                            "username": "Stafford.Cicco"
                        },
                        "firstName": "Stafford",
                        "lastName": "Cicco",
                        "specialization": {
                            "name": "CARDIO"
                        }
                    },
                    {
                        "auth": {
                            "username": "Brady.Verick"
                        },
                        "firstName": "Brady",
                        "lastName": "Verick",
                        "specialization": {
                            "name": "CARDIO"
                        }
                    },
                    {
                        "auth": {
                            "username": "Prue.Nawton"
                        },
                        "firstName": "Prue",
                        "lastName": "Nawton",
                        "specialization": {
                            "name": "CIRCUIT"
                        }
                    }
                ]
            }
            """;

    public static final String TRAINEE_PUT_REQBODY = """
            {
                "firstName": "Lola",
                "lastName": "Barthelemy",
                "isActive": false,
                "birthdate": "1953-08-14",
                "address": "666 Dunning Pass",
                "auth": {
                    "username": "Peggie.Barthelemy",
                    "password": "rD4=ob.G8"
                }
            }
            """;

    public static final String TRAINEE_PUT_RESBODY = """
            {
                "firstName": "Lola",
                "lastName": "Barthelemy",
                "isActive": false,
                "birthdate": "1953-08-14",
                "address": "666 Dunning Pass",
                "trainers": [
                    {
                        "auth": {
                            "username": "Stafford.Cicco"
                        },
                        "firstName": "Stafford",
                        "lastName": "Cicco",
                        "specialization": {
                            "name": "CARDIO"
                        }
                    },
                    {
                        "auth": {
                            "username": "Brady.Verick"
                        },
                        "firstName": "Brady",
                        "lastName": "Verick",
                        "specialization": {
                            "name": "CARDIO"
                        }
                    },
                    {
                        "auth": {
                            "username": "Prue.Nawton"
                        },
                        "firstName": "Prue",
                        "lastName": "Nawton",
                        "specialization": {
                            "name": "CIRCUIT"
                        }
                    }
                ]
            }
            """;

    public static final String TRAINEE_GET_NOT_ASIGNED_TRAINERS_RESBODY = """
            [
                {
                    "auth": {
                        "username": "Stafford.Cicco"
                    },
                    "firstName": "Stafford",
                    "lastName": "Cicco",
                    "isActive": true,
                    "specialization": {
                        "name": "CARDIO"
                    },
                    "trainees": [
                        {
                            "auth": {
                                "username": "Peggie.Barthelemy"
                            },
                            "firstName": "Lola",
                            "lastName": "Barthelemy"
                        },
                        {
                            "auth": {
                                "username": "Marne.Nuton"
                            },
                            "firstName": "Marne",
                            "lastName": "Nuton"
                        },
                        {
                            "auth": {
                                "username": "Wilbur.Casaccio"
                            },
                            "firstName": "Wilbur",
                            "lastName": "Casaccio"
                        },
                        {
                            "auth": {
                                "username": "Karel.Younghusband"
                            },
                            "firstName": "Karel",
                            "lastName": "Younghusband"
                        },
                        {
                            "auth": {
                                "username": "Tull.Burkert"
                            },
                            "firstName": "Tull",
                            "lastName": "Burkert"
                        },
                        {
                            "auth": {
                                "username": "Mina.Stenner"
                            },
                            "firstName": "Mina",
                            "lastName": "Stenner"
                        },
                        {
                            "auth": {
                                "username": "Cobby.Castagneri"
                            },
                            "firstName": "Cobby",
                            "lastName": "Castagneri"
                        }
                    ]
                },
                {
                    "auth": {
                        "username": "Prue.Nawton"
                    },
                    "firstName": "Prue",
                    "lastName": "Nawton",
                    "isActive": true,
                    "specialization": {
                        "name": "CIRCUIT"
                    },
                    "trainees": [
                        {
                            "auth": {
                                "username": "Maynard.Everix"
                            },
                            "firstName": "Maynard",
                            "lastName": "Everix"
                        },
                        {
                            "auth": {
                                "username": "Cobby.Castagneri"
                            },
                            "firstName": "Cobby",
                            "lastName": "Castagneri"
                        },
                        {
                            "auth": {
                                "username": "Marne.Nuton"
                            },
                            "firstName": "Marne",
                            "lastName": "Nuton"
                        },
                        {
                            "auth": {
                                "username": "Abbie.Bernocchi"
                            },
                            "firstName": "Abbie",
                            "lastName": "Bernocchi"
                        },
                        {
                            "auth": {
                                "username": "Cirillo.Gouldthorp"
                            },
                            "firstName": "Cirillo",
                            "lastName": "Gouldthorp"
                        },
                        {
                            "auth": {
                                "username": "Andreana.Davidzon"
                            },
                            "firstName": "Andreana",
                            "lastName": "Davidzon"
                        },
                        {
                            "auth": {
                                "username": "Peggie.Barthelemy"
                            },
                            "firstName": "Lola",
                            "lastName": "Barthelemy"
                        }
                    ]
                },
                {
                    "auth": {
                        "username": "Caryl.Shory"
                    },
                    "firstName": "Caryl",
                    "lastName": "Shory",
                    "isActive": true,
                    "specialization": {
                        "name": "CARDIO"
                    },
                    "trainees": [
                        {
                            "auth": {
                                "username": "Thaxter.Randalston"
                            },
                            "firstName": "Thaxter",
                            "lastName": "Randalston"
                        },
                        {
                            "auth": {
                                "username": "Thaxter.Randalston"
                            },
                            "firstName": "Thaxter",
                            "lastName": "Randalston"
                        }
                    ]
                },
                {
                    "auth": {
                        "username": "Rora.Loffhead"
                    },
                    "firstName": "Rora",
                    "lastName": "Loffhead",
                    "isActive": true,
                    "specialization": {
                        "name": "WEIGHT"
                    },
                    "trainees": [
                        {
                            "auth": {
                                "username": "Nial.Parry"
                            },
                            "firstName": "Nial",
                            "lastName": "Parry"
                        },
                        {
                            "auth": {
                                "username": "Jon.Sandwith"
                            },
                            "firstName": "Jon",
                            "lastName": "Sandwith"
                        },
                        {
                            "auth": {
                                "username": "Nial.Parry"
                            },
                            "firstName": "Nial",
                            "lastName": "Parry"
                        },
                        {
                            "auth": {
                                "username": "Cirillo.Gouldthorp"
                            },
                            "firstName": "Cirillo",
                            "lastName": "Gouldthorp"
                        },
                        {
                            "auth": {
                                "username": "Andreana.Davidzon"
                            },
                            "firstName": "Andreana",
                            "lastName": "Davidzon"
                        },
                        {
                            "auth": {
                                "username": "Tull.Burkert"
                            },
                            "firstName": "Tull",
                            "lastName": "Burkert"
                        },
                        {
                            "auth": {
                                "username": "Thaxter.Randalston"
                            },
                            "firstName": "Thaxter",
                            "lastName": "Randalston"
                        },
                        {
                            "auth": {
                                "username": "Maynard.Everix"
                            },
                            "firstName": "Maynard",
                            "lastName": "Everix"
                        },
                        {
                            "auth": {
                                "username": "Cirillo.Gouldthorp"
                            },
                            "firstName": "Cirillo",
                            "lastName": "Gouldthorp"
                        },
                        {
                            "auth": {
                                "username": "Marne.Nuton"
                            },
                            "firstName": "Marne",
                            "lastName": "Nuton"
                        },
                        {
                            "auth": {
                                "username": "Woodman.Mallinson"
                            },
                            "firstName": "Woodman",
                            "lastName": "Mallinson"
                        }
                    ]
                }
            ]
            """;

    public static final String TRAINEE_ADD_TRAINER_TO_LIST_REQBODY = """
            {
                "auth": {
                    "username": "Peggie.Barthelemy",
                    "password": "rD4=ob.G8"
                },
                "trainersUsernames": [
                    "not.trainer",
                    "Brady.Verick"
                ]
            }
            """;

    public static final String TRAINEE_ADD_TRAINER_TO_LIST_RESBODY = """
            [
                {
                    "auth": {
                        "username": "Brady.Verick"
                    },
                    "firstName": "Brady",
                    "lastName": "Verick",
                    "isActive": false,
                    "specialization": {
                        "name": "CARDIO"
                    },
                    "trainees": [
                        {
                            "auth": {
                                "username": "Cobby.Castagneri"
                            },
                            "firstName": "Cobby",
                            "lastName": "Castagneri"
                        },
                        {
                            "auth": {
                                "username": "Lenard.Pedgrift"
                            },
                            "firstName": "Lenard",
                            "lastName": "Pedgrift"
                        },
                        {
                            "auth": {
                                "username": "Maynard.Everix"
                            },
                            "firstName": "Maynard",
                            "lastName": "Everix"
                        },
                        {
                            "auth": {
                                "username": "Montague.Danielou"
                            },
                            "firstName": "Montague",
                            "lastName": "Danielou"
                        },
                        {
                            "auth": {
                                "username": "Andreana.Davidzon"
                            },
                            "firstName": "Andreana",
                            "lastName": "Davidzon"
                        },
                        {
                            "auth": {
                                "username": "Peggie.Barthelemy"
                            },
                            "firstName": "Lola",
                            "lastName": "Barthelemy"
                        },
                        {
                            "auth": {
                                "username": "Binky.Bulman"
                            },
                            "firstName": "Binky",
                            "lastName": "Bulman"
                        }
                    ]
                }
            ]
            """;

    public static final String TRAINEE_GET_TRAININGS_RESBODY = """
            [
                {
                    "trainer": {
                        "firstName": "Stafford",
                        "lastName": "Cicco"
                    },
                    "name": "at turpis a",
                    "type": {
                        "name": "AEROBIC"
                    },
                    "date": "2026-01-07",
                    "duration": 27
                },
                {
                    "trainer": {
                        "firstName": "Brady",
                        "lastName": "Verick"
                    },
                    "name": "erat nulla tempus vivamus",
                    "type": {
                        "name": "INTERVAL"
                    },
                    "date": "2024-12-11",
                    "duration": 42
                },
                {
                    "trainer": {
                        "firstName": "Prue",
                        "lastName": "Nawton"
                    },
                    "name": "rhoncus aliquam lacus",
                    "type": {
                        "name": "CARDIO"
                    },
                    "date": "2025-03-07",
                    "duration": 23
                }
            ]
            """;

    public static final String TRAINER_GET_RESBODY = """
            {
                "firstName": "Brady",
                "lastName": "Verick",
                "isActive": false,
                "specialization": {
                    "name": "CARDIO"
                },
                "trainees": [
                    {
                        "auth": {
                            "username": "Cobby.Castagneri"
                        },
                        "firstName": "Cobby",
                        "lastName": "Castagneri"
                    },
                    {
                        "auth": {
                            "username": "Lenard.Pedgrift"
                        },
                        "firstName": "Lenard",
                        "lastName": "Pedgrift"
                    },
                    {
                        "auth": {
                            "username": "Maynard.Everix"
                        },
                        "firstName": "Maynard",
                        "lastName": "Everix"
                    },
                    {
                        "auth": {
                            "username": "Montague.Danielou"
                        },
                        "firstName": "Montague",
                        "lastName": "Danielou"
                    },
                    {
                        "auth": {
                            "username": "Andreana.Davidzon"
                        },
                        "firstName": "Andreana",
                        "lastName": "Davidzon"
                    },
                    {
                        "auth": {
                            "username": "Peggie.Barthelemy"
                        },
                        "firstName": "Lola",
                        "lastName": "Barthelemy"
                    },
                    {
                        "auth": {
                            "username": "Binky.Bulman"
                        },
                        "firstName": "Binky",
                        "lastName": "Bulman"
                    }
                ]
            }
            """;

    public static final String TRAINER_REGISTER_REQBODY = """
            {
                "userId": "26",
                "firstName": "Brady",
                "lastName": "Verick",
                "specialization": {
                    "name": "CARDIO"
                },
                "active": false
            }
            """;

    public static final String TRAINER_REGISTER_RESBODY = """
            {
                "username": "Brady.Verick#2",
                "password": "W899/VU$=F"
            }
            """;

    public static final String TRAINER_PUT_REQBODY = """
            {
                "firstName": "Jhon",
                "lastName": "Verick",
                "isActive": true,
                "specialization": "CARDIO",
                "auth": {
                    "username": "Brady.Verick",
                    "password": "password"
                }
            }
            """;

    public static final String TRAINER_PUT_RESBODY = """
            {
                "auth": {
                    "username": "Brady.Verick"
                },
                "firstName": "Jhon",
                "lastName": "Verick",
                "isActive": true,
                "specialization": {
                    "name": "CARDIO"
                },
                "trainees": [
                    {
                        "auth": {
                            "username": "Cobby.Castagneri"
                        },
                        "firstName": "Cobby",
                        "lastName": "Castagneri"
                    },
                    {
                        "auth": {
                            "username": "Lenard.Pedgrift"
                        },
                        "firstName": "Lenard",
                        "lastName": "Pedgrift"
                    },
                ]
            }
            """;

    public static final String TRAINER_GET_TRAININGS_RESBODY = """
            [
                {
                    "trainee": {
                        "firstName": "Cobby",
                        "lastName": "Castagneri"
                    },
                    "name": "vel dapibus at diam",
                    "type": {
                        "name": "CIRCUIT"
                    },
                    "date": "2025-05-16",
                    "duration": 53
                },
                {
                    "trainee": {
                        "firstName": "Lenard",
                        "lastName": "Pedgrift"
                    },
                    "name": "donec dapibus duis at",
                    "type": {
                        "name": "INTERVAL"
                    },
                    "date": "2025-05-03",
                    "duration": 41
                },
                {
                    "trainee": {
                        "firstName": "Maynard",
                        "lastName": "Everix"
                    },
                    "name": "volutpat erat quisque erat",
                    "type": {
                        "name": "WEIGHT"
                    },
                    "date": "2025-07-13",
                    "duration": 53
                },
                {
                    "trainee": {
                        "firstName": "Montague",
                        "lastName": "Danielou"
                    },
                    "name": "adipiscing lorem vitae",
                    "type": {
                        "name": "CARDIO"
                    },
                    "date": "2025-09-23",
                    "duration": 38
                },
                {
                    "trainee": {
                        "firstName": "Andreana",
                        "lastName": "Davidzon"
                    },
                    "name": "elementum pellentesque quisque",
                    "type": {
                        "name": "PLYOMETRICS"
                    },
                    "date": "2024-11-07",
                    "duration": 8
                },
                {
                    "trainee": {
                        "firstName": "Lola",
                        "lastName": "Barthelemy"
                    },
                    "name": "erat nulla tempus vivamus",
                    "type": {
                        "name": "INTERVAL"
                    },
                    "date": "2024-12-11",
                    "duration": 42
                },
                {
                    "trainee": {
                        "firstName": "Binky",
                        "lastName": "Bulman"
                    },
                    "name": "ultrices posuere cubilia",
                    "type": {
                        "name": "PLYOMETRICS"
                    },
                    "date": "2024-05-26",
                    "duration": 8
                }
            ]
            """;

    public static final String TRAINING_CREATE_TRAINING_REQBODY = """
            {
                "auth": {
                    "username": "Peggie.Barthelemy",
                    "password": "xxxx"
                },
                "trainee": {
                    "auth": {
                        "username": "Peggie.Barthelemy"
                    }
                },
                "trainer": {
                    "auth": {
                        "username": "Stafford.Cicco"
                    }
                },
                "type": {
                    "name": "AEROBIC"
                },
                "name": "mejor entrenamiento",
                "date": "2025-07-07",
                "duration": 2
            }
            """;

    public static final String TRAINING_CREATE_TRAINING_RESBODY = """
            {
                "trainee": {
                    "auth": {
                        "username": "Peggie.Barthelemy"
                    },
                    "firstName": "Peggie",
                    "lastName": "Barthelemy"
                },
                "trainer": {
                    "auth": {
                        "username": "Stafford.Cicco"
                    },
                    "firstName": "Stafford",
                    "lastName": "Cicco"
                },
                "name": "mejor entrenamiento",
                "type": {
                    "name": "AEROBIC"
                },
                "date": "2025-07-06",
                "duration": 2
            }
            """;

    public static final String TRAINING_GET_TRAINING_TYPES_RESBODY = """
            [
                {
                    "name": "WEIGHT"
                },
                {
                    "name": "FLEXIBILITY"
                },
                {
                    "name": "BALANCE"
                },
                {
                    "name": "CIRCUIT"
                },
                {
                    "name": "INTERVAL"
                },
                {
                    "name": "CARDIO"
                },
                {
                    "name": "AEROBIC"
                },
                {
                    "name": "PLYOMETRICS"
                }
            ]
            """;
}
