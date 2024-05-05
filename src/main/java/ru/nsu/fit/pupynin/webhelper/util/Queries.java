package ru.nsu.fit.pupynin.webhelper.util;

public class Queries {

  public static final String PREFIXES =
          """
          PREFIX ontPref: <http://www.semanticweb.org/oleyn/ontologies/2022/4/кафедра#>
          PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
          PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
          
          """;

  public static final String GET_USER_ROLE_QUERY =
          PREFIXES +
                  "SELECT DISTINCT ?руководитель WHERE { " +
                  "    ?руководитель rdf:type ?тип ." +
                  "    { ?тип rdfs:subClassOf* ontPref:Руководители . }" +
                  "    ?руководитель ontPref:Почта ?emailFilter. " +
                  "}";

  public static final String GET_USER_PASSWORD_QUERY =
          PREFIXES +
                  "SELECT DISTINCT ?password WHERE { " +
                  "    ?user rdf:type ?type ." +
                  "    ?user ontPref:Почта ?email ." +
                  "    FILTER(regex(str(?email), ?emailFilter))" +
                  "    OPTIONAL {" +
                  "        ?user ontPref:Пароль ?password" +
                  "}" +
                  "}";

  public static final String GET_USER_EMAIL_QUERY =
          PREFIXES +
                  "SELECT DISTINCT ?email WHERE { " +
                  "    ?user rdf:type ?type ." +
                  "    ?user ontPref:Почта ?email ." +
                  "    FILTER(regex(str(?email), ?emailFilter))" +
                  "}";
  public static final String GET_USER_GROUP_QUERY =
          PREFIXES +
                  "SELECT DISTINCT ?group WHERE { " +
                  " ?студент ontPref:Почта ?почта ." +
                  " ?студент ontPref:группа ?group ." +
                  "FILTER(regex(str(?почта), ?emailFilter))" +
                  "}";
  public static final String GET_USER_FIO_QUERY =
          PREFIXES +
                  "SELECT DISTINCT ?fio WHERE { " +
                  " ?студент ontPref:Почта ?почта ." +
                  " ?студент ontPref:ФИО ?fio ." +
                  "FILTER(regex(str(?почта), ?emailFilter))" +
                  "}";

  public static final String GET_STUDENT_FIO_AND_GROUP_QUERY =
          PREFIXES +
                  "SELECT ?фио_студента ?группа_студента" +
                  " WHERE { " +
                  "?студент ontPref:ФИО ?фио_студента." +
                  "?студент ontPref:группа ?группа_студента." +
                  "?студент ontPref:Почта ?почта." +
                  "FILTER(regex(str(?почта), ?emailFilter))" +
                  " }";
  public static final String IS_USER_VALID =
          PREFIXES +
                  "SELECT DISTINCT ?password WHERE { " +
                  "    ?user rdf:type ?type ." +
                  "    ?user ontPref:Почта ?email ." +
                  "    FILTER(regex(str(?email), ?emailFilter))" +
                  "    OPTIONAL {" +
                  "        ?user ontPref:Пароль ?password" +
                  "    }" +
                  "}";

  public static final String GET_STUDENTS_WITH_GROUPS_CONTROL =
          PREFIXES + """
                    SELECT ?фио_студента ?почта WHERE {
                        ?студент rdf:type ?type .
                        {?type rdfs:subClassOf* ontPref:?profileFilter .}
                        ?студент ontPref:ФИО ?фио_студента .
                        ?студент ontPref:группа ?группа_студента .
                        ?студент ontPref:Почта ?почта .
                    }
                    ORDER BY ASC(?студент)
                    """;
  public static final String GET_CO_SUPERVISOR_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?cosup_fio WHERE {
                    	?student ontPref:защищает ?thesis .
                    	?cosup ontPref:руководит_с ?thesis .
                    	?cosup ontPref:ФИО ?cosup_fio.
                    } ORDER BY ASC(?cosup_fio)
                    """;

  public static final String GET_CONSULTANT_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?consultant_fio WHERE {
                    	?student ontPref:защищает ?thesis .
                    	?consultant ontPref:консультирует ?thesis .
                    	?consultant ontPref:ФИО ?consultant_fio.
                    } ORDER BY ASC(?consultant_fio)
                    """;
  public static final String GET_SUPERVISOR_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?supervisor_fio WHERE {
                    	?student ontPref:защищает ?thesis .
                    	?supervisor ontPref:согласовывает ?thesis .
                    	?supervisor ontPref:ФИО ?supervisor_fio.
                    } ORDER BY ASC(?supervisor_fio)
                    """;

  public static final String GET_REVIEWER_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?reviewer_fio WHERE {
                    	?student ontPref:защищает ?thesis .
                    	?reviewer ontPref:рецензирует ?thesis .
                    	?reviewer ontPref:ФИО ?reviewer_fio.
                    } ORDER BY ASC(?reviewer_fio)
                    """;
//ATTENTION
  public static final String GET_PRACTICE_SUPERVISOR_NSU_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?practice_supervisor_NSU_fio WHERE {
                       # ?student ontPref:на_НГУ_практике_у ?practice_supervisor_NSU .
                        ?practice_supervisor_NSU a ontPref:Руководитель_от_НГУ .
                        ?practice_supervisor_NSU ontPref:ФИО ?practice_supervisor_NSU_fio .
                    } ORDER BY ASC(?practice_supervisor_NSU_fio)
                    """;

  public static final String GET_PRACTICE_SUPERVISOR_ORG_CONTROL =
          PREFIXES + """
                    SELECT DISTINCT ?practice_supervisor_org_fio WHERE {
                        #?student ontPref:на_орг_практике_у ?practice_supervisor_org .
                        ?practice_supervisor_org a ontPref:Руководитель_от_организации .
                        ?practice_supervisor_org ontPref:ФИО ?practice_supervisor_org_fio .
                    } ORDER BY ASC(?practice_supervisor_org_fio)
                    """;

  public static final String GET_STUDENT_TEXT_DATA_CONTROL =
          PREFIXES + """
                    SELECT ?fio ?group ?thesis ?email ?practiceOrder ?thesisOrder
                          ?internshipPlaceShort ?internshipPlaceFull ?internshipPlace ?profile WHERE {
                                        
                        ?student ontPref:ФИО ?fio .
                        ?student ontPref:Почта ?email .
                    	?student ontPref:группа ?group .
                        FILTER(regex(str(?email), ?emailFilter))
                    	?student ontPref:Тема ?thesis .
                    	?student ontPref:Приказ_на_прохождение_практики ?practiceOrder .
                    	?student ontPref:Распоряжение_об_утверждении_тем_и_научных_руководителей ?thesisOrder .
                    	?student ontPref:Место_прохождения_практики ?internshipPlace .
                    	?student ontPref:Наименование_организации ?internshipPlaceShort .
                    	?student ontPref:Место_практики_полное_наименование ?internshipPlaceFull .
                    	?student ontPref:Профиль_обучения ?profile .
                                        
                    }
                    """;
  public static final String GET_STUDENT_LISTS_DATA_CONTROL =
          PREFIXES + """
                SELECT ?student_fio ?supervisor_fio ?cosup_fio ?consultant_fio
                 ?reviewer_fio ?practice_supervisor_NSU_fio ?practice_supervisor_org_fio
                WHERE {
                  ?student ontPref:ФИО ?student_fio .
                  ?student ontPref:Почта ?email .
                  FILTER(regex(str(?email), ?emailFilter))
  
                  ?student ontPref:защищает ?thesis .
                  optional {
                    ?supervisor ontPref:согласовывает ?thesis .
                    ?supervisor ontPref:ФИО ?supervisor_fio .
                  }
                  optional {
                    ?cosup ontPref:руководит_с ?thesis .
                    ?cosup ontPref:ФИО ?cosup_fio.
                  }
  
                  optional {
                    ?reviewer ontPref:рецензирует ?thesis .
                    ?reviewer ontPref:ФИО ?reviewer_fio .
                  }
  
                  optional {
                    ?consultant ontPref:консультирует ?thesis .
                    ?consultant ontPref:ФИО ?consultant_fio .
                  }
  
                  optional {
                    ?student ontPref:на_НГУ_практике_у ?practice_supervisor_NSU .
                    ?practice_supervisor_NSU ontPref:ФИО ?practice_supervisor_NSU_fio .
                  }
  
                  optional {
                    ?student ontPref:на_орг_практике_у ?practice_supervisor_org .
                    ?practice_supervisor_org ontPref:ФИО ?practice_supervisor_org_fio .
                  }

                }
                    """;

  public static final String GET_SUB_CLASSES_OF_STUDENTS =
          PREFIXES + """                
                    SELECT?subClass WHERE {
                       ?subClass rdfs:subClassOf ontPref:Учащиеся.
                    }
                    """;

  public static final String GET_STUDENTS_ALL =
          PREFIXES +
                  "SELECT DISTINCT ?фио WHERE { " +
                  "?студент rdf:type ?тип ." +
                  " {?тип rdfs:subClassOf* ontPref:Учащиеся . } " +
                  "?студент ontPref:ФИО ?фио ." +
                  " } ORDER BY ASC(?фио)";

  public static final String GET_STUDENT_CO_SUPERVISOR_QUERY =
          PREFIXES +
                  "SELECT ?cosup_fio WHERE { " +
                  "?data ontPref:Почта ?email ." +
                  " FILTER(regex(str(?email), ?emailFilter ))" +
                  "?data ontPref:защищает ?fkw ." +
                  "OPTIONAL { " +
                  "?cosup ontPref:руководит_с ?fkw ." +
                  "?cosup ontPref:ФИО ?cosup_fio ." +
                  " }" +
                  "}";

  public static final String GET_STUDENT_MASTER_PROFILE_QUERY =
          PREFIXES +
                  "SELECT ?profile WHERE { " +
                  "?data ontPref:Почта ?email ." +
                  "FILTER(regex(str(?email), ?emailFilter))" +
                  "?data ontPref:Профиль_обучения ?profile ." +
                  "}";

  public static final String GET_SUPERVISOR_INFO_QUERY =
          PREFIXES + """
                    SELECT ?sup_fio ?sup_title ?sup_degree ?sup_badge  WHERE {
                        ?student ontPref:Почта ?email .
                        FILTER(regex(str(?email), ?emailFilter))

                        ?student ontPref:защищает ?thesis .

                        ?supervisor ontPref:согласовывает ?thesis .
                        ?supervisor ontPref:ФИО ?sup_fio .
                        ?supervisor ontPref:Должность_руководителя_ВКР ?sup_title .
                        ?supervisor ontPref:Ученая_степень_руководителя_ВКР ?sup_degree.
                        optional {
                            ?supervisor ontPref:Ученое_звание ?sup_badge .
                        }
                    }
                    """;
  public static final String GET_SUPERVISOR_NSU_INFO_QUERY =
          PREFIXES + """
                    SELECT ?sup_nsu_fio ?sup_nsu_title ?sup_nsu_degree ?sup_nsu_badge WHERE {
                    	?student ontPref:Почта ?email .
                    	FILTER(regex(str(?email), ?emailFilter))
                    	
                    	?student ontPref:защищает ?thesis .
                    	
                    	?student ontPref:на_НГУ_практике_у ?sup_nsu .
                    	?sup_nsu ontPref:ФИО ?sup_nsu_fio .
                    	?sup_nsu ontPref:Должность_в_НГУ ?sup_nsu_title .
                    	optional {
                    		?sup_nsu ontPref:Ученая_степень_руководителя_ВКР ?sup_nsu_degree .
                    		?sup_nsu ontPref:Ученое_звание ?sup_nsu_badge .
                    	}
                    }
                    """;
  public static final String GET_SUPERVISOR_ORG_INFO_QUERY =
          PREFIXES + """
                    SELECT ?sup_org_fio ?sup_org_title ?sup_org_degree ?sup_org_badge WHERE {
                        ?student ontPref:Почта ?email .
                        FILTER(regex(str(?email), ?emailFilter))
                        
                        ?student ontPref:защищает ?thesis .
                        
                        ?student ontPref:на_орг_практике_у ?sup_org .
                        ?sup_org ontPref:ФИО ?sup_org_fio .
                        ?sup_org ontPref:Должность_в_организации ?sup_org_title .
                        optional {
                            ?sup_org ontPref:Ученая_степень_руководителя_ВКР ?sup_org_degree .
                            ?sup_org ontPref:Ученое_звание ?sup_org_badge .
                        }
                    }
                    """;
  public static final String GET_CO_SUPERVISOR_INFO_QUERY =
          PREFIXES + """
                   SELECT ?co_sup_fio ?co_sup_title ?co_sup_degree ?co_sup_badge WHERE {
                        ?student ontPref:Почта ?email .
                        FILTER(regex(str(?email), ?emailFilter))
                        
                        ?student ontPref:защищает ?thesis .
                        
                        ?co_sup ontPref:руководит_с ?thesis .
                        ?co_sup ontPref:ФИО ?co_sup_fio .
                        ?co_sup ontPref:Должность_в_НГУ ?co_sup_title .
                        optional {
                            ?co_sup ontPref:Ученая_степень_руководителя_ВКР ?co_sup_degree .
                            ?co_sup ontPref:Ученое_звание ?co_sup_badge .
                        }
                    }
                    """;
  public static final String GET_CONSULTANT_INFO_QUERY =
          PREFIXES + """
                   SELECT ?consultant_fio ?consultant_title ?consultant_degree ?consultant_badge WHERE {
                        ?student ontPref:Почта ?email .
                        FILTER(regex(str(?email), ?emailFilter))
                        
                        ?student ontPref:защищает ?thesis .
                        
                        ?consultant ontPref:консультирует ?thesis .
                        ?consultant ontPref:ФИО ?consultant_fio .
                        ?consultant ontPref:Должность ?consultant_title .
                        optional {
                            ?consultant ontPref:Ученая_степень ?consultant_degree .
                            ?consultant ontPref:Ученое_звание ?co_sup_badge .
                        }
                    }
                    """;
  public static final String GET_REVIEWER_INFO_QUERY =
          PREFIXES + """
                   SELECT ?reviewer_fio ?reviewer_title ?reviewer_degree ?reviewer_badge WHERE {
                        ?student ontPref:Почта ?email .
                    	FILTER(regex(str(?email), ?emailFilter))
                        
                        ?student ontPref:защищает ?thesis .
                        
                        ?reviewer ontPref:рецензирует ?thesis .
                        ?reviewer ontPref:ФИО ?reviewer_fio .
                        ?reviewer ontPref:Должность ?reviewer_title .
                        optional {
                            ?reviewer ontPref:Ученая_степень ?reviewer_degree .
                            ?reviewer ontPref:Ученое_звание ?reviewer_badge .
                        }
                    }
                    """;

  //////////////////////////// UPDATE //////////////////////////////

  public static final String UPDATE_USER_PASSWORD_QUERY =
          PREFIXES +
                  "DELETE {\n" +
                  " ?user ontPref:Пароль ?password .\n" +
                  "}\n" +
                  "INSERT {\n" +
                  " ?user ontPref:Пароль ?newPassword .\n" +
                  "}\n" +
                  "WHERE {\n" +
                  " ?user rdf:type ?type .\n" +
                  " ?user ontPref:Почта ?email .\n" +
                  " ?user ontPref:Пароль ?password .\n" +
                  " FILTER(regex(str(?email), ?emailFilter))\n" +
                  "}";


  public static final String UPDATE_STUDENT_FIO =
          PREFIXES + """
                  DELETE {
                    ?student ontPref:ФИО ?fio .
                  }
                  INSERT {
                    ?student ontPref:ФИО ?fioFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:ФИО ?fio .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;

public static final String UPDATE_THESIS_FKW_AND_STUDENT =
        PREFIXES + """
                  DELETE {
                    ?student ontPref:Тема ?title .
                    ?thesis ontPref:Тема ?title .
                  }
                  INSERT {
                    ?student ontPref:Тема ?titleFilter .
                    ?thesis ontPref:Тема ?titleFilter .
                  }
                  WHERE {
                    ?thesis rdf:type ontPref:ВКР .
                    ?thesis ontPref:Тема ?title .
                    
                    ?student rdf:type ?type .
                    { ?type rdfs:subClassOf* ontPref:Учащиеся . }
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Тема ?title .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;

public static final String UPDATE_STUDENT_GROUP =
        PREFIXES + """
                  DELETE {
                    ?student ontPref:группа ?group .
                  }
                  INSERT {
                    ?student ontPref:группа ?groupFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:группа ?group .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
public static final String UPDATE_STUDENT_EMAIL =
        PREFIXES + """
                  DELETE{
                    ?student ontPref:Почта ?email .
                  }
                  INSERT {
                    ?student ontPref:Почта ?newEmailFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;

  public static final String UPDATE_STUDENT_PRACTICE_ORDER =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Приказ_на_прохождение_практики ?practiceOrder .
                  }
                  INSERT {
                    ?student ontPref:Приказ_на_прохождение_практики ?practiceOrderFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Приказ_на_прохождение_практики ?practiceOrder .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
  public static final String UPDATE_STUDENT_THESIS_ORDER =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Распоряжение_об_утверждении_тем_и_научных_руководителей ?thesisOrder .
                  }
                  INSERT {
                    ?student ontPref:Распоряжение_об_утверждении_тем_и_научных_руководителей ?thesisOrderFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Распоряжение_об_утверждении_тем_и_научных_руководителей ?thesisOrder .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
  public static final String UPDATE_STUDENT_INTERNSHIP_PLACE_SHORT =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Наименование_организации ?internshipPlaceShort .
                  }
                  INSERT {
                    ?student ontPref:Наименование_организации ?internshipPlaceShortFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Наименование_организации ?internshipPlaceShort .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
  public static final String UPDATE_STUDENT_INTERNSHIP_PLACE_FULL =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Место_практики_полное_наименование ?internshipPlaceFull .
                  }
                  INSERT {
                    ?student ontPref:Место_практики_полное_наименование ?internshipPlaceFullFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Место_практики_полное_наименование ?internshipPlaceFull .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
  public static final String UPDATE_STUDENT_INTERNSHIP_PLACE =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Место_прохождения_практики ?internshipPlace .
                  }
                  INSERT {
                    ?student ontPref:Место_прохождения_практики ?internshipPlaceFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Место_прохождения_практики ?internshipPlace .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;
  public static final String UPDATE_STUDENT_PROFILE =
          PREFIXES + """
                  DELETE{
                    ?student ontPref:Профиль_обучения ?profile .
                  }
                  INSERT {
                    ?student ontPref:Профиль_обучения ?profileFilter .
                  }
                  WHERE {
                    ?student ontPref:Почта ?email .
                    ?student ontPref:Профиль_обучения ?profile .
                    FILTER(regex(str(?email), ?emailFilter))
                  }
                  """;

}