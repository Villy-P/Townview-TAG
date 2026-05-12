.ONESHELL:

craps-reset:
	rmdir craps/build
	mkdir craps/build
	cd craps/build
	cmake -GNinja ..

craps-run:
	ninja -C craps/build
	.\craps\build\Craps.exe

monopoly-reset:
	rmdir monopoly/build
	mkdir monopoly/build
	cd monopoly/build
	cmake -GNinja ..

monopoly-run:
	ninja -C monopoly/build
	.\monopoly\build\Monopoly.exe

chess-run:
	cd chess
	tsc -w

poker-run:
	sbcl.exe --eval '(require "asdf")' --eval '(asdf:load-asd (merge-pathnames "poker/project.asd" (uiop:getcwd)))' --eval '(asdf:load-system :project)'