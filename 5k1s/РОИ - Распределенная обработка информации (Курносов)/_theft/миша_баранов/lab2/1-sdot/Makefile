prog := sdot
prog_objs := sdot.o

CC := gcc
CFLAGS := -Wall -O2 -msse -std=c99 -mavx
LDFLAGS := -lm

.PHONY: all clean

all: $(prog)

$(prog): $(prog_objs)
	$(CC) -o $@ $^ $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

sdot.o: sdot.c

clean:
	@rm -rf *.o $(prog)
