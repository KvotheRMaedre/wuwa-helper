package com.wuwa.helper.services;

import com.wuwa.helper.dto.WeaponTypeDTO;
import com.wuwa.helper.entity.WeaponType;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeaponTypeServiceTest {

    @Mock
    private WeaponTypeRepository weaponTypeRepository;

    @InjectMocks
    private WeaponTypeService weaponTypeService;

    @Captor
    private ArgumentCaptor<WeaponType> weaponTypeArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createWeaponType{

        @Test
        @DisplayName("Should create weapon type with success")
        void shouldCreateWeaponTypeWithSuccess(){
            var weaponTypeDTO = new WeaponTypeDTO("Broadblade");
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    weaponTypeDTO.name(),
                    Instant.now(),
                    null
            );
            doReturn(weaponType)
                    .when(weaponTypeRepository)
                    .save(weaponTypeArgumentCaptor.capture());

            var response = weaponTypeService.createWeaponType(weaponTypeDTO);

            assertNotNull(response);
            assertInstanceOf(UUID.class, response);
            assertEquals(weaponTypeDTO.name(), weaponTypeArgumentCaptor.getValue().getName());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            var weaponTypeDTO = new WeaponTypeDTO("Broadblade");
            doReturn(new RuntimeException())
                    .when(weaponTypeRepository)
                    .save(any());
            assertThrows(RuntimeException.class, () -> weaponTypeService.createWeaponType(weaponTypeDTO));
        }
    }

    @Nested
    class getWeaponById{

        @Test
        @DisplayName("Should return weapon type if it exists")
        void shouldReturnWeaponTypeIfItExists(){
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    "Broadblade",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(weaponType))
                    .when(weaponTypeRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = weaponTypeService.getWeaponTypeById(weaponType.getId().toString());

            assertTrue(response.isPresent());
            assertEquals(weaponType.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should return empty if the weapon type doesn't exists")
        void shouldReturnEmptyIfTheWeaponTypeDoesntExists(){
            var weaponTypeId = UUID.randomUUID();
            doReturn(Optional.empty())
                    .when(weaponTypeRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = weaponTypeService.getWeaponTypeById(weaponTypeId.toString());

            assertFalse(response.isPresent());
            assertEquals(weaponTypeId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class getAllWeapon{

        @Test
        @DisplayName("Should return one weapon type if theres only one registered")
        void ShouldReturnOneWeaponTypeIfTheresOnlyOneRegistered(){
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    "Broadblade",
                    Instant.now(),
                    null
            );

            doReturn(List.of(weaponType))
                    .when(weaponTypeRepository)
                    .findAll();

            var listWeaponType = weaponTypeService.getAllWeaponType();

            assertNotNull(listWeaponType);
            assertEquals(1, listWeaponType.size());
        }

        @Test
        @DisplayName("Should return all weapon types with success when theres more than 1")
        void shouldReturnAllWeaponTypesWithSuccessWhenTheresOnlyMoreThanOne(){
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    "Broadblade",
                    Instant.now(),
                    null
            );
            var weaponType2 = new WeaponType(
                    UUID.randomUUID(),
                    "Sword",
                    Instant.now(),
                    null
            );

            var userList = List.of(weaponType, weaponType2);
            doReturn(userList)
                    .when(weaponTypeRepository)
                    .findAll();

            var response = weaponTypeService.getAllWeaponType();

            assertNotNull(response);
            assertEquals(userList.size(), response.size());
        }

        @Test
        @DisplayName("Should return all weapon types with success when theres none")
        void shouldReturnAllWeaponTypesWithSuccessWhenTheresNoneWeaponType(){
            doReturn(List.of())
                    .when(weaponTypeRepository)
                    .findAll();

            var response = weaponTypeService.getAllWeaponType();

            assertNotNull(response);
            assertEquals(0, response.size());
        }
    }

    @Nested
    class weaponTypeExists{

        @Test
        @DisplayName("Should return true if the weapon type exist")
        void shouldRetunTrueIfTheWeaponTypeExist(){
            var weaponTypeId = UUID.randomUUID();
            doReturn(true)
                    .when(weaponTypeRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var response = weaponTypeService.weaponTypeExists(weaponTypeId.toString());

            assertTrue(response);
            assertEquals(weaponTypeId, uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should return false if the weapon type doesn't exist")
        void shouldRetunFalseIfTheWeaponTypeDoesntExist(){
            var weaponTypeId = UUID.randomUUID();
            doReturn(false)
                    .when(weaponTypeRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var response = weaponTypeService.weaponTypeExists(weaponTypeId.toString());

            assertFalse(response);
            assertEquals(weaponTypeId, uuidArgumentCaptor.getValue());
        }

    }

    @Nested
    class deleteWeaponTypeById{

        @Test
        @DisplayName("Should delete the weapon type with success")
        void shouldDeleteWeaponTypeWithSuccess(){
            var weaponTypeId = UUID.randomUUID();
            doReturn(true)
                    .when(weaponTypeRepository)
                    .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(weaponTypeRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            weaponTypeService.deleteWeaponTypeById(weaponTypeId.toString());

            var listIds = uuidArgumentCaptor.getAllValues();
            assertEquals(weaponTypeId, listIds.getFirst());
            assertEquals(weaponTypeId, listIds.getLast());
            verify(weaponTypeRepository, times(1)).deleteById(uuidArgumentCaptor.capture());
            verify(weaponTypeRepository, times(1)).existsById(uuidArgumentCaptor.capture());
        }

        @Test
        @DisplayName("Should do nothing if the weapon doesn't exist")
        void shouldDoNothingIfTheWeaponDoenstExist(){
            var weaponTypeId = UUID.randomUUID();
            doReturn(false)
                    .when(weaponTypeRepository)
                    .existsById(uuidArgumentCaptor.capture());

            weaponTypeService.deleteWeaponTypeById(weaponTypeId.toString());

            assertEquals(weaponTypeId, uuidArgumentCaptor.getValue());
            verify(weaponTypeRepository, times(0)).deleteById(uuidArgumentCaptor.capture());
            verify(weaponTypeRepository, times(1)).existsById(uuidArgumentCaptor.capture());
        }
    }

    @Nested
    class updateWeaponType{

        @Test
        @DisplayName("Should update the weapon type with success")
        void shouldUpdateTheWeaponTypeWithSuccess(){
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    "Broadblade",
                    Instant.now(),
                    null
            );
            var weaponTypeDTO = new WeaponTypeDTO("Sword");
            var weaponTypeSaved = new WeaponType(
                    weaponType.getId(),
                    "Sword",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(weaponType))
                    .when(weaponTypeRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(weaponTypeSaved)
                    .when(weaponTypeRepository)
                    .save(weaponTypeArgumentCaptor.capture());

            var response = weaponTypeService.updateWeaponType(weaponType.getId().toString(), weaponTypeDTO);

            assertNotNull(response);
            assertEquals(weaponType.getId(), uuidArgumentCaptor.getValue());
            assertEquals(weaponType.getName(), weaponTypeArgumentCaptor.getValue().getName());
            verify(weaponTypeRepository, times(1)).findById(uuidArgumentCaptor.capture());
            verify(weaponTypeRepository, times(1)).save(weaponTypeArgumentCaptor.capture());
        }

        @Test
        @DisplayName("Should do nithing if the weapon type doesnt exist")
        void shouldDoNothingIfTheWeaponTypeDoenstExist(){
            var weaponType = new WeaponType(
                    UUID.randomUUID(),
                    "Broadblade",
                    Instant.now(),
                    null
            );
            var weaponTypeDTO = new WeaponTypeDTO("Sword");
            var weaponTypeSaved = new WeaponType(
                    weaponType.getId(),
                    "Sword",
                    Instant.now(),
                    null
            );
            doReturn(Optional.empty())
                    .when(weaponTypeRepository)
                    .findById(uuidArgumentCaptor.capture());

            var response = weaponTypeService.updateWeaponType(weaponType.getId().toString(), weaponTypeDTO);

            assertNull(response);
            assertEquals(weaponType.getId(), uuidArgumentCaptor.getValue());
            verify(weaponTypeRepository, times(1)).findById(uuidArgumentCaptor.capture());
            verify(weaponTypeRepository, times(0)).save(weaponTypeArgumentCaptor.capture());
        }
    }

}