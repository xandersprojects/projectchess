
JFLAGS = -g -Xlint:unchecked -Xlint:deprecation

%.class: %.java
	javac $(JFLAGS) $<

JAVA_SRCS =  $(wildcard *.java)

.PHONY: default style clean

default: sentinel 

sentinel: $(JAVA_SRCS)
	$(RM) sentinel
	javac $(JFLAGS) $^
	touch sentinel

clean:
	rm -f *.class *.o *~ sentinel