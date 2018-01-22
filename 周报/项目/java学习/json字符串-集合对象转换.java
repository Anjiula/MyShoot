    
    //使用Jackson 包的  ObjectMapper类  
    
    
    /**
     * 将对象合集转换为json字符串
     * 
     * @param list
     * @return
     */
    protected String convertToJsonData(Object list) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            // mapper.setDateFormat(new
            // SimpleDateFormat(DateTime.DATE_FORMAT_DATEONLY));
            String liststr = mapper.writeValueAsString(list);
            return liststr;
        } catch (JsonProcessingException jsone) {
            log.error(jsone.getMessage(), jsone);
            throw new RuntimeException(jsone);
        }
    }


    /**
     * 转换json字符串到对象集合
     * 
     * @param json
     * @param parametrized
     * @param parameterClasses
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    protected <U, S extends Collection<U>> S convertJsonStringToObjects(String json, Class<S> parametrized,
            Class<U> parameterClasses) throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        // mapper.setDateFormat(new SimpleDateFormat(DateTime.DATE_FORMAT_DATEONLY));
        JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        S collection = mapper.readValue(json, javaType);
        return collection;
    }


    /**
     * 转换json字符串到对象
     * 
     * @param json
     * @param parameterClass
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    protected <T> T convertJsonStringToObject(String json, Class<T> parameterClass)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        T object = mapper.readValue(json, parameterClass);
        return object;
    }