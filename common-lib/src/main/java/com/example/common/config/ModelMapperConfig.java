package com.example.common.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.print.attribute.standard.Destination;

/**
 * 配置 ModelMapper
 *
 * @author Leo
 * @date 2020.02.24
 */
@Configuration
public class ModelMapperConfig {

	@Bean(value = "modelMapper")
//	@Scope("prototype")
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
//				.setFullTypeMatchingRequired(true)
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean(value = "modelMapperDeepCopy")
	public ModelMapper modelMapperDeepCopy() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setDeepCopyEnabled(true)
				.setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

//	@Bean
//	@Scope("singleton")
//	public ModelMapper getModelMapper() {
//		ModelMapper modelMapper = new ModelMapper();
//
//		//默认为 standard 模式，设置为 strict 模式
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//		// 类型映射代码
//		sourceSonToDestinationSon(modelMapper);
//		sourceToDestination(modelMapper);
//
//		//验证映射
//		modelMapper.validate();
//
//		// 配置代码
//		return modelMapper;
//	}
//
//	/**
//	 * 声明 Source 类转 Destination 类的 Mapper
//	 *
//	 * @param modelMapper
//	 */
//	private void sourceSonToDestinationSon(ModelMapper modelMapper) {
//		modelMapper.createTypeMap(SouSubClass.class, DestSubClass.class)
//				.addMapping(SouSubClass::getSonId, DestSubClass::setDsonId)
//				.addMapping(SouSubClass::getSonName, DestSubClass::setSonName)
//				.addMappings(mapper -> mapper.skip(DestSubClass::setExcessParam));
//	}
//
//	private void sourceToDestination(ModelMapper modelMapper) {
//		modelMapper.createTypeMap(Source.class, Destination.class)
//				.addMappings(mapper -> mapper.using((Converter<Integer, Integer>) context -> {
//					if (context.getSource() == null) {
//						return 18;
//					}
//					return context.getSource();
//				}).map(Source::getAge, Destination::setAge))
//				.addMappings(mapper -> mapper.using((Converter<Date, Date>) context -> {
//					if (context.getSource() == null) {
//						return new Date();
//					}
//					return context.getSource();
//				}).map(Source::getCreateTime, Destination::setCreateTime))
//				.addMapping(Source::getSourceSon, Destination::setDestinationSon)
//				.addMapping(Source::getListSon, Destination::setSonList)
//				.addMapping(Source::getMapSon, Destination::setSonMap)
//				.addMappings(mapper -> mapper.skip(Destination::setExcessParam));
//	}
}
