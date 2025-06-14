cmake_minimum_required(VERSION 3.10)
project(MyProject)

set(CMAKE_CXX_STANDARD 17)

file(GLOB SOURCES "${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp")

include_directories(include/)

add_executable(SPARKA ${SOURCES})

set_target_properties(SPARKA PROPERTIES 
                      RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}")

target_compile_options(SPARKA PRIVATE 
                       "-Wall" 
                       "-std=c++17"
                       "-fdiagnostics-color=always"
                       "-g"
                       "-m64"
                       )

target_link_libraries(SPARKA 
                      "glew32"
                      "opengl32"
                      "glu32"
                      "opengl32"
                      "freeglut"
                      "libboost_filesystem-mt")